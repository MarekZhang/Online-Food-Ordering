package com.imooc.takeaway.enums;

import lombok.Getter;

@Getter
public enum PaymentStatusEnum implements StatusEnum{
  PENDING(0, "waiting for payment"),
  PAID(1, "order has been paid");

  private Integer code;
  private String msg;
  PaymentStatusEnum(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

}
