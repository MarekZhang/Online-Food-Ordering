package com.imooc.takeaway.service.impl;

import com.imooc.takeaway.dto.OrderDTO;
import com.imooc.takeaway.service.PayService;
import com.imooc.takeaway.utils.JsonUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service("PayService")
@Slf4j
public class PayServiceImpl implements PayService {
  public static final String ORDER_NAME = "online food order";

  @Autowired
  BestPayServiceImpl bestPayService;

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
    log.info("[wechat payment] request={}", JsonUtil.toJSON(payRequest));

    PayResponse payResponse = bestPayService.pay(payRequest);

    log.info("[wechat payment] response={}", JsonUtil.toJSON(payResponse));

    return payResponse;
  }
}
