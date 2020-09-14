package com.imooc.takeaway.service.impl;

import com.imooc.takeaway.domain.ProductCategory;
import com.imooc.takeaway.service.ProductCategoryService;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest extends TestCase {
  @Resource(name = "ProductCategoryService")
  ProductCategoryService service;

  @Test
  public void testFindAll() {
    List<ProductCategory> categories = service.findAll();
    Assert.assertNotEquals(0, categories.size());
  }

  @Test
  public void testFindByCategoryType() {
    List<ProductCategory> categories = service.findByCategoryType(Arrays.asList(1, 11, 10));
    Assert.assertNotEquals(0, categories.size());
  }

  @Test
  public void testFindOne() {
    ProductCategory cat = service.findOne(6);
    Assert.assertEquals(new Integer(1), cat.getCategoryType());
  }

  @Test
  public void testSave() {
    ProductCategory category = new ProductCategory();
    category.setCategoryName("SASIMI");
    category.setCategoryType(2);

    Assert.assertNotNull(service.save(category));
  }
}