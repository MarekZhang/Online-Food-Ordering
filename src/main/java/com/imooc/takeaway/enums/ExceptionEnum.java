package com.imooc.takeaway.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
  ORDER_CANCELLED_SUCCESSFULLY(0, "Order cancelled"),
  PARAM_ERROR(1, "invalid form param"),
  PRODUCT_NOT_EXIST(10, "product does not exist"),
  PRODUCT_STOCK_ERROR(11, "invalid stock number"),
  ORDER_NOT_EXIST(12, "order does not exist"),
  DETAIL_NOT_EXIST(13, "order detail not exist"),
  ORDER_STATUS_ERROR(14, "order status error, cannot cancel order"),
  ORDER_UPDATE_ERROR(15, "update order error"),
  ORDER_DETAIL_EMPTY(16,"empty order detail"),
  PAYMENT_STATUS_ERROR(17, "payment status error"),
  CART_EMPTY_ERROR(18, "cart cannot be empty"),
  UNAUTHORIZED_USER(19, "order does not belong to current user"),
  WECHAT_MPSERVICE_ERROR(20, "weChat mp service error"),
  ASYNC_NOTIFICATION_ORDER_AMOUNT_ERROR(21, "inconsistent order amount"),
  ORDER_COMPLETE_SUCCESSFULLY(22, "order completed"),
  PRODUCT_STATUS_ERROR(23, "product status error"),
  UNAUTHORIZED_VENDOR_ERROR(24, "unauthorized vendor user, failed to login"),
  SUCCESSFULLY_LOGOUT(25, "successfully logged out")
  ;
  private Integer code;
  private String msg;

  ExceptionEnum(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}
