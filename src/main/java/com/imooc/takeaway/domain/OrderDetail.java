package com.imooc.takeaway.domain;

import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class OrderDetail {
  @Id
  private String detailId;

  private String orderId;

  private String productId;

  private String productName;

  private BigDecimal productPrice;

  private Integer productQuantity;

  private String productIcon;

  private Date createTime;

  private Date updateTime;
}
