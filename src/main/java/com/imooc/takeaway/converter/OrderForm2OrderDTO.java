package com.imooc.takeaway.converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import com.imooc.takeaway.domain.OrderDetail;
import com.imooc.takeaway.dto.OrderDTO;
import com.imooc.takeaway.enums.ExceptionEnum;
import com.imooc.takeaway.exception.OrderException;
import com.imooc.takeaway.form.OrderForm;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderForm2OrderDTO {
  public static OrderDTO convert(OrderForm orderForm) {
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setBuyerName(orderForm.getName());
    orderDTO.setBuyerPhone(orderForm.getPhone());
    orderDTO.setBuyerAddress(orderForm.getAddress());
    orderDTO.setBuyerOpenid(orderForm.getOpenid());
    Gson gson = new Gson();
    List<OrderDetail> orderDetailList = new ArrayList<>();
    try {
      orderDetailList = gson.fromJson(orderForm.getItems(),
              new TypeToken<List<OrderDetail>>() {
              }.getType());
    } catch (Exception e) {
      log.error("[Object covert] error, {}", orderForm.getItems());
      throw new OrderException(ExceptionEnum.PARAM_ERROR);
    }

    orderDTO.setOrderDetailList(orderDetailList);
    return orderDTO;
  }
}

