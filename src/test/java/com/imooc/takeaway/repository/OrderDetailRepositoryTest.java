package com.imooc.takeaway.repository;

import com.imooc.takeaway.domain.OrderDetail;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest{

  @Autowired
  OrderDetailRepository repository;

  @Test
  public void saveTest() {
    OrderDetail detail = new OrderDetail();
    detail.setDetailId("2");
    detail.setOrderId("112");
    detail.setProductId("3");
    detail.setProductName("小龙虾");
    detail.setProductPrice(new BigDecimal(100));
    detail.setProductQuantity(2);
    detail.setProductIcon("http://xxxxx.jpg");

    Assert.assertNotNull(repository.save(detail));
  }

  @Test
  public void testFindByOrderId() {
    List<OrderDetail> list = repository.findByOrderId("112");
    Assert.assertNotEquals(0, list.size());
  }
}