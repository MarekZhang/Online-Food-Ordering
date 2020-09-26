package com.imooc.takeaway.exception;

import com.imooc.takeaway.enums.ExceptionEnum;

import lombok.Getter;

@Getter
public class OrderException extends RuntimeException {
  private Integer code;

  public OrderException(ExceptionEnum exceptionEnum) {
    super(exceptionEnum.getMsg());
    this.code = exceptionEnum.getCode();
  }

  public OrderException(int errorCode, String errorMsg) {
    super(errorMsg);
    this.code = errorCode;
  }
}
