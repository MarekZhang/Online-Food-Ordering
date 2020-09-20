package com.imooc.takeaway.service.impl;

import com.imooc.takeaway.dto.OrderDTO;
import com.imooc.takeaway.enums.ExceptionEnum;
import com.imooc.takeaway.exception.OrderException;
import com.imooc.takeaway.service.BuyerService;
import com.imooc.takeaway.service.OrderService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Service("BuyerService")
@Slf4j
public class BuyerServiceImpl implements BuyerService {
  @Resource(name = "OrderService")
  OrderService orderService;

  @Override
  public OrderDTO findOrderOne(String openid, String orderId) {
    return authorization(openid, orderId);
  }

  @Override
  public void cancelOrder(String openid, String orderId) {
    OrderDTO orderDTO = authorization(openid, orderId);
    orderService.cancel(orderDTO);
  }

  private OrderDTO authorization(String openid, String orderID) {
    OrderDTO orderDTO = orderService.findOne(orderID);
    if (orderDTO == null) {
      log.error("[cancel order], order does not exists");
      throw new OrderException(ExceptionEnum.ORDER_NOT_EXIST);
    }
    if (!openid.equalsIgnoreCase(orderDTO.getBuyerOpenid())) {
      log.error("[cancel order], order does not belong to current user openid={}, orderDTO={}", openid, orderDTO);
      throw new OrderException(ExceptionEnum.UNAUTHORIZED_USER);
    }
    return orderDTO;
  }
}
