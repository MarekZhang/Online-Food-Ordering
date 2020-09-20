package com.imooc.takeaway.service.impl;

import com.imooc.takeaway.dto.OrderDTO;
import com.imooc.takeaway.service.OrderService;
import com.imooc.takeaway.service.PayService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {
  @Autowired
  PayService payService;

  @Autowired
  OrderService orderService;

  @Test
  public void create(){
    OrderDTO orderDTO = orderService.findOne("1600345269956636321");
    payService.create(orderDTO);
  }

  @Test
  public void refund() {
    OrderDTO orderDTO = orderService.findOne("1600601146524909988");
    payService.cancel(orderDTO);
  }

}