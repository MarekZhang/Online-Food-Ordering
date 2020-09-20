package com.imooc.takeaway.service;

import com.imooc.takeaway.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

public interface PayService {
  PayResponse create(OrderDTO orderDTO);
  PayResponse notify(String notifyData);
  RefundResponse cancel(OrderDTO orderDTO);
}
