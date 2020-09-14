package com.imooc.takeaway.service.impl;

import com.imooc.takeaway.domain.ProductInfo;
import com.imooc.takeaway.enums.ProductInfoEnum;
import com.imooc.takeaway.service.ProductInfoService;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest extends TestCase {

  @Resource(name="ProductInfoService")
  ProductInfoService service;

  @Test
  public void testFindAll() {
    Page<ProductInfo> page = service.findAll(new PageRequest(1, 2));
//    System.out.println(page.getTotalElements());
    Assert.assertNotEquals(0, page.getTotalElements());
  }

  @Test
  public void testFindOnSell() {
    List<ProductInfo> list = service.findOnSell();
    Assert.assertNotEquals(0, list.size());
  }

  @Test
  public void testFindOne() {
    ProductInfo productInfo = service.findOne("#111");
    Assert.assertEquals("#111", productInfo.getProductId());
  }

  @Test
  @Transactional
  public void testSave() {
    ProductInfo productInfo = new ProductInfo();
    productInfo.setProductId("#113");
    productInfo.setProductName("牛肉饭");
    productInfo.setProductPrice(new BigDecimal("30.5"));
    productInfo.setProductStock(10);
    productInfo.setProductDescription("非常好吃");
    productInfo.setProductIcon("Http://xxxxx.jpg");
    productInfo.setCategoryType(10);
    productInfo.setProductStatus(ProductInfoEnum.ONSELL.getStatus());

    ProductInfo info = service.save(productInfo);
    Assert.assertNotNull(info);
  }
}