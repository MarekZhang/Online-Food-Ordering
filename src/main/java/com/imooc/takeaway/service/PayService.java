package com.imooc.takeaway.service;

import com.imooc.takeaway.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;

public interface PayService {
  PayResponse create(OrderDTO orderDTO);
}
