package com.imooc.takeaway.service.impl;

import com.imooc.takeaway.converter.OrderMaster2OrderDTOConverter;
import com.imooc.takeaway.domain.OrderDetail;
import com.imooc.takeaway.domain.OrderMaster;
import com.imooc.takeaway.domain.ProductInfo;
import com.imooc.takeaway.dto.CartDTO;
import com.imooc.takeaway.dto.OrderDTO;
import com.imooc.takeaway.enums.ExceptionEnum;
import com.imooc.takeaway.enums.OrderStatusEnum;
import com.imooc.takeaway.enums.PaymentStatusEnum;
import com.imooc.takeaway.exception.OrderException;
import com.imooc.takeaway.repository.OrderDetailRepository;
import com.imooc.takeaway.repository.OrderMasterRepository;
import com.imooc.takeaway.service.OrderService;
import com.imooc.takeaway.service.PayService;
import com.imooc.takeaway.service.ProductInfoService;
import com.imooc.takeaway.utils.KeyUtil;

import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service("OrderService")
@Slf4j
public class OrderServiceImpl implements OrderService {
  @Autowired
  ProductInfoService productInfoService;
  @Autowired
  OrderDetailRepository orderDetailRepository;
  @Autowired
  OrderMasterRepository orderMasterRepository;
  @Autowired
  WebSocket webSocket;
  @Resource(name = "PayService")
  PayService payService;

  @Override
  @Transactional
  public OrderDTO create(OrderDTO orderDTO) {
    BigDecimal totalAmount = new BigDecimal(0);
    String orderId = KeyUtil.getUID();
    //1.query productInfo by using the data in List<OrderDetail>
    for (OrderDetail detail : orderDTO.getOrderDetailList()) {
      ProductInfo productInfo = productInfoService.findOne(detail.getProductId());
      if(productInfo == null)
        throw new OrderException(ExceptionEnum.PRODUCT_NOT_EXIST);
      //2. calculate the total amount
      totalAmount = productInfo.getProductPrice()
              .multiply(new BigDecimal(detail.getProductQuantity()))
              .add(totalAmount);
      BeanUtils.copyProperties(productInfo, detail);
      detail.setDetailId(KeyUtil.getUID());
      detail.setOrderId(orderId);
      //save order detail in DB
      orderDetailRepository.save(detail);
    }

    //3. save master order in DB
    OrderMaster orderMaster = new OrderMaster();
    orderDTO.setOrderId(orderId);
    BeanUtils.copyProperties(orderDTO, orderMaster);
    orderMaster.setOrderAmount(totalAmount);
    orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
    orderMaster.setPayStatus(PaymentStatusEnum.PENDING.getCode());

    orderMasterRepository.save(orderMaster);
    //4. revise the number of stock
    List<CartDTO> cartDTOS = orderDTO.getOrderDetailList().stream().
            map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
    productInfoService.decreaseStock(cartDTOS);

    //5. send message to web socket
    webSocket.broadcast(orderDTO.getOrderId());

    return orderDTO;
  }

  @Override
  @Transactional
  public OrderDTO findOne(String orderId) {
    OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
    if(orderMaster == null)
      throw new OrderException(ExceptionEnum.ORDER_NOT_EXIST);
    List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
    if (orderDetails.size() == 0)
      throw new OrderException(ExceptionEnum.ORDER_NOT_EXIST);

    OrderDTO orderDTO = new OrderDTO();
    BeanUtils.copyProperties(orderMaster, orderDTO);
    orderDTO.setOrderDetailList(orderDetails);

    return orderDTO;
  }

  @Override
  @Transactional
  public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
    // the detail of order is unnecessary
    Page<OrderMaster> orderMasterPages = orderMasterRepository.findByBuyerOpenidIn(buyerOpenid, pageable);

    List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPages.getContent());

    Page<OrderDTO> orderDTOPages = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPages.getTotalElements());

    return orderDTOPages;
  }

  @Override
  @Transactional
  public OrderDTO cancel(OrderDTO orderDTO) {
    //1. find the order to be cancelled
    String orderId = orderDTO.getOrderId();
    OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
    if (orderMaster == null) {
      log.error("[Cancel order error]Find Order Error orderStatus={}, orderId={}", orderMaster.getOrderStatus(), orderMaster.getOrderId());
      throw new OrderException(ExceptionEnum.ORDER_NOT_EXIST);
    }
    //2. change the order status
    if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
      log.error("[Cancel order error]Order Status Error orderMaster={}", orderMaster);
      throw new OrderException(ExceptionEnum.ORDER_STATUS_ERROR);
    }
    orderDTO.setOrderStatus(OrderStatusEnum.CANCELLED.getCode());
    //update orderMaster information
    BeanUtils.copyProperties(orderDTO, orderMaster);
    OrderMaster resultOrder = orderMasterRepository.save(orderMaster);
    if(resultOrder==null){
      log.error("[Cancel Order Error]fail to cancel order, orderMaster={}", orderMaster);
      throw new OrderException(ExceptionEnum.ORDER_UPDATE_ERROR);
    }

    if (orderDTO.getOrderDetailList().size() == 0) {
      log.error("[Cancel order Error] No order details, orderDTO={}", orderDTO);
      throw new OrderException(ExceptionEnum.ORDER_DETAIL_EMPTY);
    }

    //3. change the stock
    List<CartDTO> cart = orderDTO.getOrderDetailList().stream()
                    .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());

    productInfoService.increaseStock(cart);
    //4. refund
    if (orderDTO.getPayStatus().equals(PaymentStatusEnum.PAID.getCode())) {
      payService.cancel(orderDTO);
    }

    return orderDTO;
  }

  @Override
  @Transactional
  public OrderDTO complete(OrderDTO orderDTO) {
    //1.check order status
    if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
      log.error("[Complete order] order status error, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
      throw new OrderException(ExceptionEnum.ORDER_STATUS_ERROR);
    }
    //2.change order status
    OrderMaster orderMaster = new OrderMaster();
    orderDTO.setOrderStatus(OrderStatusEnum.COMPLETED.getCode());
    BeanUtils.copyProperties(orderDTO, orderMaster);
    OrderMaster result = orderMasterRepository.save(orderMaster);
    if (result == null) {
      log.error("[Complete order] change order status error, orderMaster={}", orderMaster);
      throw new OrderException(ExceptionEnum.ORDER_UPDATE_ERROR);
    }

    return orderDTO;
  }

  @Override
  @Transactional
  public OrderDTO pay(OrderDTO orderDTO) {
    //1.check order status
    if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
      log.error("[Complete order] order status error, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
      throw new OrderException(ExceptionEnum.ORDER_STATUS_ERROR);
    }
    OrderMaster orderMaster = new OrderMaster();
    //2.check payment status
    if (!orderDTO.getPayStatus().equals(PaymentStatusEnum.PENDING.getCode())) {
      log.error("[Pay order] payment status error, orderDTO={}", orderDTO);
      throw new OrderException(ExceptionEnum.PAYMENT_STATUS_ERROR);
    }

    //3.change payment status
    orderDTO.setPayStatus(PaymentStatusEnum.PAID.getCode());
    BeanUtils.copyProperties(orderDTO, orderMaster);
    OrderMaster result = orderMasterRepository.save(orderMaster);
    if (result == null) {
      log.error("[Pay order] pay order error, orderMaster={}", orderMaster);
      throw new OrderException(ExceptionEnum.ORDER_UPDATE_ERROR);
    }

    return orderDTO;
  }

  @Override
  public Page<OrderDTO> findAll(Pageable pageable) {
    Page<OrderMaster> orderMasterPages = orderMasterRepository.findAll(pageable);
    List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPages.getContent());
    Page<OrderDTO> orderDTOPages = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPages.getTotalElements());

    return orderDTOPages;
  }
}
