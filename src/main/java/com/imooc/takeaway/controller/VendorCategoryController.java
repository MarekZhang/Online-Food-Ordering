package com.imooc.takeaway.controller;

import com.imooc.takeaway.domain.ProductCategory;
import com.imooc.takeaway.domain.ProductInfo;
import com.imooc.takeaway.enums.ExceptionEnum;
import com.imooc.takeaway.exception.OrderException;
import com.imooc.takeaway.form.CategoryForm;
import com.imooc.takeaway.service.ProductCategoryService;
import com.imooc.takeaway.service.ProductInfoService;

import org.simpleframework.xml.core.Validate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/vendor/category")
@Slf4j
public class VendorCategoryController {
  @Resource(name = "ProductInfoService")
  ProductInfoService productInfoService;

  @Resource(name = "ProductCategoryService")
  ProductCategoryService productCategoryService;

  @GetMapping("/list")
  public ModelAndView list(Map<String, Object> map) {
    List<ProductCategory> categoryList = productCategoryService.findAll();
    map.put("categoryList", categoryList);
    return new ModelAndView("vendor/category/list", map);
  }

  @GetMapping("/index")
  public ModelAndView index(@RequestParam(name = "categoryId", required = false) Integer categoryId,
                            Map<String, Object> map) {
    if (categoryId == null) {
      return new ModelAndView("vendor/category/index");
    } else {
      try {
        ProductCategory productCategory = productCategoryService.findOne(categoryId);
        map.put("prevCategoryType", productCategory.getCategoryType());
        map.put("productCategory", productCategory);
      } catch (Exception e) {
        log.error("[vendor check category] product category error {}", e.getMessage());
        map.put("msg", e.getMessage());
        map.put("url", "/sell/vendor/category/list");
        return new ModelAndView("common/error", map);
      }

      return new ModelAndView("vendor/category/index", map);
    }
  }

  @PostMapping("/save")
  public ModelAndView save(@Validate CategoryForm categoryForm,
                           BindingResult bindingResult,
                           Map<String, Object> map) {
    if (bindingResult.hasErrors()) {
      log.error("[Modify category] Invalid form params categoryForm={}", categoryForm);
      throw new OrderException(ExceptionEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
    }

    ProductCategory productCategory = new ProductCategory();
    Integer categoryId = categoryForm.getCategoryId();

    if(categoryId!=null && !categoryForm.getCategoryType().equals(categoryForm.getPrevCategoryType())){
      //find all products obtaining this category
      try {
        productCategory = productCategoryService.findOne(categoryId);

        List<ProductInfo> productInfoList = productInfoService.findByCategoryType(categoryForm.getPrevCategoryType());
        if(!productInfoList.isEmpty()){
          //update all products
          for (ProductInfo productInfo : productInfoList) {
            productInfo.setCategoryType(categoryForm.getCategoryType());
            productInfoService.save(productInfo);
          }
        }
      } catch (Exception e) {
        log.error("[Modify category] error: {}", e.getMessage());
        map.put("msg", e.getMessage());
        map.put("url", "/sell/vendor/category/list");
        return new ModelAndView("common/error", map);
      }
    }

    BeanUtils.copyProperties(categoryForm, productCategory);
    productCategoryService.save(productCategory);
    map.put("msg", "Successfully modified category");
    map.put("url", "/sell/vendor/category/list");

    return new ModelAndView("common/success", map);
  }
}
