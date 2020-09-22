package com.imooc.takeaway.repository;

import com.imooc.takeaway.domain.ProductInfo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
  List<ProductInfo> findByProductStatusIn(Integer status);

  List<ProductInfo> findByCategoryTypeIn(Integer categoryType);
}
