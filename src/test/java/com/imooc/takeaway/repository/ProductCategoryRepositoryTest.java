package com.imooc.takeaway.repository;

import com.imooc.takeaway.domain.ProductCategory;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
  @Autowired
  ProductCategoryRepository rep;

  @Test
  public void insert() {
    ProductCategory productCategory = rep.findOne(1);
    productCategory.setCategoryName("SUSHI");
    productCategory.setCategoryType(11);
    rep.save(productCategory);
  }

  @Test
  public void findOne() {
    ProductCategory productCategory = rep.findOne(1);
    System.out.println(productCategory);
  }

  @Test
  @Transactional
  public void saveTest() {
    ProductCategory productCategory = new ProductCategory();
    productCategory.setCategoryName("Fried Staff");
    productCategory.setCategoryType(1);
    ProductCategory productCategory1 = rep.save(productCategory);
    Assert.assertNotNull(productCategory1);
  }

  @Test
  public void findByCategoryTypeInTest() {
    List<ProductCategory> list = rep.findByCategoryTypeIn(Arrays.asList(10, 11, 12));
    Assert.assertNotEquals(0, list.size());
  }
}