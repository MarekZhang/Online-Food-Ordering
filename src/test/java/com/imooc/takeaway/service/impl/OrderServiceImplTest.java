package com.imooc.takeaway.service.impl;

import com.imooc.takeaway.domain.OrderDetail;
import com.imooc.takeaway.dto.OrderDTO;
import com.imooc.takeaway.enums.OrderStatusEnum;
import com.imooc.takeaway.enums.PaymentStatusEnum;
import com.imooc.takeaway.service.OrderService;

import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
  private final String OPEN_ID = "moonayy";
  private final String ORDER_ID = "1596699641599107946";

  @Resource(name="OrderService")
  OrderService service;

  @Test
  public void create() {
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setBuyerAddress("Hampton Lodge");
    orderDTO.setBuyerName("Mark");
    orderDTO.setBuyerOpenid(OPEN_ID);
    orderDTO.setBuyerPhone("894999073");
    List<OrderDetail> detailList = new ArrayList<>();
    OrderDetail o1 = new OrderDetail();
    o1.setProductId("#110");
    o1.setProductQuantity(5);
    OrderDetail o2 = new OrderDetail();
    o2.setProductQuantity(6);
    o2.setProductId("#114");
    detailList.add(o1);
    detailList.add(o2);
    orderDTO.setOrderDetailList(detailList);

    OrderDTO saveDTO = service.create(orderDTO);
    log.info("[create new order] result={}", saveDTO);
    Assert.assertNotNull(saveDTO);

  }

  @Test
  public void findOne() {
    OrderDTO dto = service.findOne(ORDER_ID);
    log.info("[find order by order id] result{}", dto);
    Assert.assertNotNull(dto);
  }

  @Test
  public void findList() {
    Page<OrderDTO> dtoPage = service.findList(OPEN_ID, new PageRequest(0, 3));
    log.info("[find order list by WeChat id] result{}", dtoPage.getContent());
    Assert.assertNotEquals(0, dtoPage.getTotalElements());
  }

  @Test
  public void cancel() {
    OrderDTO dto = service.findOne(ORDER_ID);
    dto = service.cancel(dto);
    log.info("[cancel order] result{}", dto);
    Assert.assertEquals(dto.getOrderStatus(), OrderStatusEnum.CANCELLED.getCode());
  }

  @Test
  public void complete() {
    OrderDTO dto = service.findOne(ORDER_ID);
    dto = service.complete(dto);
    log.info("[complete order] result{}", dto);
    Assert.assertEquals(dto.getOrderStatus(),OrderStatusEnum.COMPLETED.getCode());
  }

  @Test
  public void pay() {
    OrderDTO dto = service.findOne(ORDER_ID);
    dto = service.pay(dto);
    log.info("[pay order] result{}", dto);
    Assert.assertEquals(dto.getPayStatus(), PaymentStatusEnum.PAID.getCode());
  }
}