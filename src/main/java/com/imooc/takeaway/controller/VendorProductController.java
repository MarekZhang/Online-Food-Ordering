package com.imooc.takeaway.controller;

import com.imooc.takeaway.domain.ProductCategory;
import com.imooc.takeaway.domain.ProductInfo;
import com.imooc.takeaway.enums.ExceptionEnum;
import com.imooc.takeaway.exception.OrderException;
import com.imooc.takeaway.form.ProductForm;
import com.imooc.takeaway.service.ProductCategoryService;
import com.imooc.takeaway.service.ProductInfoService;
import com.imooc.takeaway.utils.KeyUtil;

import org.simpleframework.xml.core.Validate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@Slf4j
@RequestMapping("/vendor/product")
public class VendorProductController {
  @Resource(name = "ProductInfoService")
  ProductInfoService productInfoService;

  @Resource(name = "ProductCategoryService")
  ProductCategoryService productCategoryService;

  /**
   * list all products
   *
   * @param page
   * @param size
   * @param map
   * @return
   */
  @GetMapping("/list")
  public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                           Map<String, Object> map) {
    PageRequest pageRequest = new PageRequest(page - 1, size);
    Page<ProductInfo> productList = productInfoService.findAll(pageRequest);
    map.put("productList", productList);
    map.put("page", page);
    map.put("size", size);
    return new ModelAndView("vendor/product/list", map);
  }

  /**
   * display product
   *
   * @param productId
   * @param map
   * @return
   */
  @GetMapping("/display")
  public ModelAndView display(@RequestParam("productId") String productId,
                              Map<String, Object> map) {
    try {
      ProductInfo productInfo = productInfoService.display(productId);
    } catch (Exception e) {
      log.error("[change product status] error={}", e.getMessage());
      e.printStackTrace();
      map.put("msg", e.getMessage());
      map.put("url", "/sell/vendor/product/list");
      return new ModelAndView("common/error", map);
    }

    map.put("msg", "Successfully displayed product");
    map.put("url", "/sell/vendor/product/list");

    return new ModelAndView("common/success", map);
  }

  /**
   * product sold out
   *
   * @param productId
   * @param map
   * @return
   */
  @GetMapping("/offShelf")
  public ModelAndView offShelf(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
    try {
      ProductInfo productInfo = productInfoService.offShelf(productId);
    } catch (Exception e) {
      log.error("[change product status] error: {}", e.getMessage());
      e.printStackTrace();
      map.put("msg", e.getMessage());
      map.put("url", "/sell/vendor/product/list");
      return new ModelAndView("common/error", map);
    }

    map.put("msg", "Successfully take product off shelf");
    map.put("url", "/sell/vendor/product/list");

    return new ModelAndView("common/success", map);
  }

  /**
   * used for adding a new product or revising an existing product
   *
   * @param productId
   * @param map
   * @return
   */
  @GetMapping("/index")
  public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                            Map<String, Object> map) {

    List<ProductCategory> categoryList = productCategoryService.findAll();
    map.put("categoryList", categoryList);

    //adding a new product
    if (productId == null) {
      return new ModelAndView("vendor/order/index", map);
    }

    //updating an existing product
    ProductInfo productInfo = null;
    try {
      productInfo = productInfoService.findOne(productId);
    } catch (Exception e) {
      log.error("[update product] error: {}", e.getMessage());
      e.printStackTrace();
      map.put("msg", e.getMessage());
      map.put("url", "/sell/vendor/product/list");
      return new ModelAndView("common/error", map);
    }
    map.put("productInfo", productInfo);

    return new ModelAndView("vendor/order/index", map);
  }

  @PostMapping("/save")
  public ModelAndView save(@Validate ProductForm productForm,
                           BindingResult bindingResult,
                           Map<String, Object> map) {
    if (bindingResult.hasErrors()) {
      log.error("[Create order] Invalid form params productForm={}", productForm);
      throw new OrderException(ExceptionEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
    }

    ProductInfo productInfo = new ProductInfo();

    //add new a product
    try {
      if(productForm.getProductId()==null || productForm.getProductId().length()==0){
        productForm.setProductId(KeyUtil.getUID());
      }else{
        productInfo = productInfoService.findOne(productForm.getProductId());
      }
      BeanUtils.copyProperties(productForm, productInfo);
    } catch (Exception e) {
      log.error("[save product] error: {}", e.getMessage());
      e.printStackTrace();
      map.put("msg", e.getMessage());
      map.put("url", "/sell/vendor/product/list");
      return new ModelAndView("common/error", map);
    }

    map.put("msg", "Successfully updated product");
    map.put("url", "/sell/vendor/product/list");
    productInfoService.save(productInfo);

    return new ModelAndView("common/success", map);
  }
}
