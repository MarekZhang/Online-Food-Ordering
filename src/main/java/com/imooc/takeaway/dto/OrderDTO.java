package com.imooc.takeaway.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.takeaway.domain.OrderDetail;
import com.imooc.takeaway.enums.OrderStatusEnum;
import com.imooc.takeaway.enums.PaymentStatusEnum;
import com.imooc.takeaway.enums.StatusEnum;
import com.imooc.takeaway.utils.EnumUtil;
import com.imooc.takeaway.utils.serializer.Date2LongSerializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
  private String orderId;

  //name of buyer
  private String buyerName;

  //phone of buyer
  private String buyerPhone;

  //buyer address
  private String buyerAddress;

  //buyer wechat Id
  private String buyerOpenid;

  //order amount
  private BigDecimal orderAmount;

  //order status default value is 0 represents new order
  private Integer orderStatus;

  //payment status default value is 0 represents to be paid
  private Integer payStatus;

  @JsonSerialize(using = Date2LongSerializer.class)
  private Date createTime;

  @JsonSerialize(using = Date2LongSerializer.class)
  private Date updateTime;

  private List<OrderDetail> orderDetailList;

  //should not be transferred to the front end
  @JsonIgnore
  public OrderStatusEnum getOrderStatusEnum() {
    return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
  }

  //should not be transferred to the front end
  @JsonIgnore
  public PaymentStatusEnum getPaymentStatusEnum() {
    return EnumUtil.getByCode(payStatus, PaymentStatusEnum.class);
  }
}
