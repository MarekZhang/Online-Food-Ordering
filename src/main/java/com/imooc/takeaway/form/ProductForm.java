package com.imooc.takeaway.form;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductForm {

  private String productId;

  private String productName;

  private BigDecimal productPrice;

  private Integer productStock;

  //default status is on display
  private Integer productStatus = 0;

  private String productDescription;

  private String productIcon;

  private Integer categoryType;

}
