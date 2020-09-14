package com.imooc.takeaway.repository;

import com.imooc.takeaway.domain.OrderMaster;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest extends TestCase {

  @Autowired
  OrderMasterRepository repository;

  @Test
  public void testSave() {
    OrderMaster orderMaster = new OrderMaster();
    orderMaster.setOrderId("113");
    orderMaster.setBuyerName("May");
    orderMaster.setBuyerPhone("794999074");
    orderMaster.setBuyerAddress("Drummartin Terrace");
    orderMaster.setBuyerOpenid("avc124");
    orderMaster.setOrderAmount(new BigDecimal(160));

    OrderMaster add = repository.save(orderMaster);
    Assert.assertNotNull(add);
  }

  @Test
  public void testFindByBuyerOpenidIn() {
    PageRequest pageRequest = new PageRequest(0, 1);
    Page<OrderMaster> page = repository.findByBuyerOpenidIn("avc124", pageRequest);
    Assert.assertNotEquals(0, page.getTotalElements());
  }
}