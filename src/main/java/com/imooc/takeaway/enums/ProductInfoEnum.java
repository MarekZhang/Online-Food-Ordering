package com.imooc.takeaway.enums;

import lombok.Getter;

@Getter
public enum ProductInfoEnum {
  ONSELL(0,"the product is on sell"),
  SOLDOUT(1, "the product is sold out");

  private Integer status;
  private String message;

  ProductInfoEnum(Integer status, String message) {
    this.status = status;
    this.message = message;
  }
}
