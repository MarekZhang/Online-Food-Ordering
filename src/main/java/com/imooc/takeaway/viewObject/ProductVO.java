package com.imooc.takeaway.viewObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ProductVO implements Serializable {

  private static final long serialVersionUID = 778096621754425978L;

  @JsonProperty("name")
  private String categoryName;

  @JsonProperty("type")
  private Integer categoryType;

  @JsonProperty("foods")
  private List<ProductInfoVO> productList;
}
