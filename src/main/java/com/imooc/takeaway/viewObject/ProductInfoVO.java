package com.imooc.takeaway.viewObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductInfoVO implements Serializable {

  private static final long serialVersionUID = -3307915248482920491L;

  @JsonProperty("id")
  private String productId;

  @JsonProperty("name")
  private String productName;

  @JsonProperty("price")
  private BigDecimal productPrice;

  @JsonProperty("description")
  private String productDescription;

  @JsonProperty("icon")
  private String productIcon;
}
