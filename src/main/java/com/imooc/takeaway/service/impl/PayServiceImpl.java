package com.imooc.takeaway.service.impl;

import com.imooc.takeaway.dto.OrderDTO;
import com.imooc.takeaway.enums.ExceptionEnum;
import com.imooc.takeaway.exception.OrderException;
import com.imooc.takeaway.service.OrderService;
import com.imooc.takeaway.service.PayService;
import com.imooc.takeaway.utils.JsonUtil;
import com.imooc.takeaway.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

@Service("PayService")
@Slf4j
public class PayServiceImpl implements PayService {
  public static final String ORDER_NAME = "online food order";

  @Autowired
  BestPayServiceImpl bestPayService;

  @Autowired
  OrderService orderService;

  @Override
  //return the prepay order
  public PayResponse create(OrderDTO orderDTO) {
    PayRequest payRequest = new PayRequest();
  /**
   * hardcode the openid,借用账号需要填写依附于其他公众号的openid
   * 使用个人公众号时从数据库取openid
   * payRequest.setOpenid(orderDTO.getBuyerOpenid());
   */
    payRequest.setOpenid("oTgZpwSGwQ-uDrwwjzVvZyllY80o");
    payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
    payRequest.setOrderId(orderDTO.getOrderId());
    payRequest.setOrderName(ORDER_NAME);
    payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
    log.info("[weChat payment] request={}", JsonUtil.toJSON(payRequest));

    PayResponse payResponse = bestPayService.pay(payRequest);

    log.info("[weChat payment] response={}", JsonUtil.toJSON(payResponse));

    return payResponse;
  }

  @Override
  public PayResponse notify(String notifyData) {
    PayResponse payResponse = bestPayService.asyncNotify(notifyData);
    log.info("[weChat async notification], payResponse={}", JsonUtil.toJSON(payResponse));
    /**
     * weChat payment document emphasizes that following verifications are essential
     * 1.signature verification (has been done in the sdk)
     * 2.payment status (successful, failed, pended...)
     * 3.order amount
     */
     //order id verification
    OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
    if(orderDTO == null){
      log.error("[weChat async notification] invalid orderId: {}", payResponse.getOrderId());
      throw new OrderException(ExceptionEnum.ORDER_NOT_EXIST);
    }

    //order amount verification
    BigDecimal orderAmount = orderDTO.getOrderAmount();
    Double responseOrderAmount = payResponse.getOrderAmount();
    if (!MathUtil.amountVerification(orderAmount.doubleValue(), responseOrderAmount)) {
      log.error("[weChat async notification] inconsistent order amount");
      throw new OrderException((ExceptionEnum.ASYNC_NOTIFICATION_ORDER_AMOUNT_ERROR));
    }
    orderService.pay(orderDTO);

    return payResponse;
  }

  @Override
  public RefundResponse cancel(OrderDTO orderDTO) {
    RefundRequest refundRequest = new RefundRequest();
    refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
    refundRequest.setOrderId(orderDTO.getOrderId());
    refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
    log.info("[weChat cancel order] refund request={}", JsonUtil.toJSON(refundRequest));
    RefundResponse refundResponse = bestPayService.refund(refundRequest);
    log.info("[weChat cancel order] refund response={}", JsonUtil.toJSON(refundResponse));

    return refundResponse;
  }
}
