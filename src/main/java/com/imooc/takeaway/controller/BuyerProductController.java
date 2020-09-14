package com.imooc.takeaway.controller;

import com.imooc.takeaway.domain.ProductCategory;
import com.imooc.takeaway.domain.ProductInfo;
import com.imooc.takeaway.service.ProductCategoryService;
import com.imooc.takeaway.service.ProductInfoService;
import com.imooc.takeaway.utils.ResultVOUtil;
import com.imooc.takeaway.viewObject.ProductInfoVO;
import com.imooc.takeaway.viewObject.ProductVO;
import com.imooc.takeaway.viewObject.ResultVO;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
  @Resource(name = "ProductCategoryService")
  ProductCategoryService categoryService;

  @Resource(name = "ProductInfoService")
  ProductInfoService productInfoService;

  @GetMapping("/list")
  public ResultVO list() {
    //1.find all on sale product
    List<ProductInfo> productInfoList = productInfoService.findOnSell();
    //2.find all category
    List<ProductCategory> categoryList = categoryService.findAll();

    List<ProductVO> productVOList = new ArrayList<>();
    for (ProductCategory productCategory : categoryList) {
      ProductVO productVO = new ProductVO();
      productVO.setCategoryName(productCategory.getCategoryName());
      productVO.setCategoryType(productCategory.getCategoryType());

      List<ProductInfoVO> productInfoVOList = new ArrayList<>();
      for (ProductInfo productInfo : productInfoList) {
        if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
          ProductInfoVO productInfoVO = new ProductInfoVO();
          //fast copy similar object
          BeanUtils.copyProperties(productInfo, productInfoVO);
          productInfoVOList.add(productInfoVO);
        }
      }
      productVO.setProductList(productInfoVOList);
      productVOList.add(productVO);
    }

    return ResultVOUtil.success(productVOList);
  }
}
