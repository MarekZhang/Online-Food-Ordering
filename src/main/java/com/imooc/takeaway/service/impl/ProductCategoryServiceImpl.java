package com.imooc.takeaway.service.impl;

import com.imooc.takeaway.domain.ProductCategory;
import com.imooc.takeaway.repository.ProductCategoryRepository;
import com.imooc.takeaway.service.ProductCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService {
  @Autowired
  ProductCategoryRepository repository;

  @Override
  public List<ProductCategory> findAll() {
    return repository.findAll();
  }

  @Override
  public List<ProductCategory> findByCategoryType(List<Integer> typeList) {
    return repository.findByCategoryTypeIn(typeList);
  }

  @Override
  public ProductCategory findOne(Integer productId) {
    return repository.findOne(productId);
  }

  @Override
  public ProductCategory save(ProductCategory category) {
    return repository.save(category);
  }
}
