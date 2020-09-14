package com.imooc.takeaway.domain;

import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class ProductInfo {

  @Id
  private String productId;

  private String productName;

  private BigDecimal productPrice;

  private Integer productStock;

  private Integer productStatus;

  private String productDescription;

  private String productIcon;

  private Integer categoryType;
}
