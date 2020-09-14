package com.imooc.takeaway.service;

import com.imooc.takeaway.domain.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
  List<ProductCategory> findAll();

  List<ProductCategory> findByCategoryType(List<Integer> typeList);

  ProductCategory findOne(Integer productId);

  ProductCategory save(ProductCategory category);
}
