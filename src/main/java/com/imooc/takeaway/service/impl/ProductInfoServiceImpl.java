package com.imooc.takeaway.service.impl;

import com.imooc.takeaway.domain.ProductInfo;
import com.imooc.takeaway.dto.CartDTO;
import com.imooc.takeaway.enums.ExceptionEnum;
import com.imooc.takeaway.enums.ProductInfoEnum;
import com.imooc.takeaway.exception.OrderException;
import com.imooc.takeaway.repository.ProductInfoRepository;
import com.imooc.takeaway.service.ProductInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Service("ProductInfoService")
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService {

  @Autowired
  ProductInfoRepository repository;

  @Override
  public Page<ProductInfo> findAll(Pageable pageable) {
    //the first page with two items
    return repository.findAll(pageable);
  }

  @Override
  public List<ProductInfo> findOnSell() {
    return repository.findByProductStatusIn(ProductInfoEnum.ONSELL.getStatus());
  }

  @Override
  public ProductInfo findOne(String productId) {
    return repository.findOne(productId);
  }

  @Override
  public ProductInfo save(ProductInfo productInfo) {
    return repository.save(productInfo);
  }

  @Override
  public void decreaseStock(List<CartDTO> cart) {
    for (CartDTO cartDTO : cart) {
      ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
      if(productInfo == null)
        throw new OrderException(ExceptionEnum.PRODUCT_NOT_EXIST);
      int newStock = productInfo.getProductStock() - cartDTO.getProductQuantity();
      if(newStock < 0)
        throw new OrderException(ExceptionEnum.PRODUCT_STOCK_ERROR);
      productInfo.setProductStock(newStock);
      repository.save(productInfo);
    }
  }

  @Override
  public void increaseStock(List<CartDTO> cart) {
    for (CartDTO cartDTO : cart) {
      ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
      if(productInfo == null)
        throw new OrderException(ExceptionEnum.PRODUCT_NOT_EXIST);
      int newStock = productInfo.getProductStock() + cartDTO.getProductQuantity();

      productInfo.setProductStock(newStock);
      repository.save(productInfo);
    }
  }

  @Override
  public ProductInfo display(String productId) {
    ProductInfo productInfo = repository.findOne(productId);
    if (productInfo == null) {
      throw new OrderException(ExceptionEnum.PRODUCT_NOT_EXIST);
    }
    if (productInfo.getProductStatus() == 0) {
      throw new OrderException(ExceptionEnum.PRODUCT_STATUS_ERROR);
    }
    //display product; 0 represents on sell
    productInfo.setProductStatus(0);
    return repository.save(productInfo);
  }

  @Override
  public ProductInfo offShelf(String productId) {
    ProductInfo productInfo = repository.findOne(productId);
    if (productInfo == null) {
      throw new OrderException(ExceptionEnum.PRODUCT_NOT_EXIST);
    }
    if (productInfo.getProductStatus() == 1) {
      throw new OrderException(ExceptionEnum.PRODUCT_STATUS_ERROR);
    }
    //display product; 0 represents on sell
    productInfo.setProductStatus(1);
    return repository.save(productInfo);
  }
}
