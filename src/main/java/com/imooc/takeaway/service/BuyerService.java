package com.imooc.takeaway.service;

import com.imooc.takeaway.dto.OrderDTO;

public interface BuyerService {
  //find detail of given order
  OrderDTO findOrderOne(String openid, String orderId);

  //cancel the given order
  void cancelOrder(String openid, String orderId);
}
