package com.imooc.takeaway.service;

import com.imooc.takeaway.domain.ProductInfo;
import com.imooc.takeaway.dto.CartDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {
  /**
   * the seller get all products with pagination
   */
  Page<ProductInfo> findAll(Pageable pageable);

  /**
   * the customer get all on sell products
   */
  List<ProductInfo> findOnSell();

  /**
   * find by product id
   */
  ProductInfo findOne(String productId);

  /**
   * save a new product
   */
  ProductInfo save(ProductInfo productInfo);

  /**
   * decrease the stock
   */
  void decreaseStock(List<CartDTO> cart);

  /**
   * increase the stock
   */
  void increaseStock(List<CartDTO> cart);

  /**
   * product on shelf
   * @param productId
   * @return
   */
  ProductInfo display(String productId);

  /**
   * product off shelf
   * @param productId
   * @return
   */
  ProductInfo offShelf(String productId);
}
