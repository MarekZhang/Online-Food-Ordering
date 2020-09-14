package com.imooc.takeaway.domain;

import com.imooc.takeaway.enums.OrderStatusEnum;
import com.imooc.takeaway.enums.PaymentStatusEnum;

import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {
  @Id
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
  private Integer orderStatus = OrderStatusEnum.NEW.getCode();

  //payment status default value is 0 represents to be paid
  private Integer payStatus = PaymentStatusEnum.PENDING.getCode();

  private Date createTime;

  private Date updateTime;

}
