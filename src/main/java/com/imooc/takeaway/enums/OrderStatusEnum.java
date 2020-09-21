package com.imooc.takeaway.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements StatusEnum{
  NEW(0, "new order"),
  COMPLETED(1, "order completed"),
  CANCELLED(2, "order cancelled");

  private Integer code;
  private String msg;

  OrderStatusEnum(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}
