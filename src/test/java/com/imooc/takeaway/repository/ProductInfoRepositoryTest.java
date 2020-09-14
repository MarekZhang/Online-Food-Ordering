package com.imooc.takeaway.repository;

import com.imooc.takeaway.domain.ProductInfo;
import com.imooc.takeaway.enums.ProductInfoEnum;

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
public class ProductInfoRepositoryTest extends TestCase {
  @Autowired
  ProductInfoRepository repository;

  @Test
  public void testFindByProductStatusIn() {
    List<ProductInfo> productInfoList = repository.findByProductStatusIn(0);
    Assert.assertNotEquals(0, productInfoList.size());
  }

  @Test
  public void testSave() {
    ProductInfo productInfo = new ProductInfo();
    productInfo.setProductId("#112");
    productInfo.setProductName("鸡腿饭");
    productInfo.setProductPrice(new BigDecimal("20.5"));
    productInfo.setProductStock(10);
    productInfo.setProductDescription("非常好吃");
    productInfo.setProductIcon("Http://xxxxx.jpg");
    productInfo.setCategoryType(10);
    productInfo.setProductStatus(ProductInfoEnum.ONSELL.getStatus());

    ProductInfo info = repository.save(productInfo);
    Assert.assertNotNull(info);
  }
}