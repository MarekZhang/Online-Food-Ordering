package com.imooc.takeaway.service;

import com.imooc.takeaway.dto.OrderDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
  //1.create new order
  OrderDTO create(OrderDTO orderDTO);

  //2.find an order
  OrderDTO findOne(String orderId);

  //3.find orders by wechat openid
  Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

  //4.cancel order
  OrderDTO cancel(OrderDTO orderDTO);

  //5.complete an order
  OrderDTO complete(OrderDTO orderDTO);

  //6.pay for an order
  OrderDTO pay(OrderDTO orderDTO);

  //7.find all orders -- used for the vendor side
  Page<OrderDTO> findAll(Pageable pageable);
}
