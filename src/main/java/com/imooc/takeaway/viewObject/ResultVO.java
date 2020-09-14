package com.imooc.takeaway.viewObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResultVO<T> {
  private Integer code;
  @JsonProperty("msg")
  private String message;
  private T data;
}
