package com.imooc.takeaway.viewObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResultVO<T> implements Serializable {
  private static final long serialVersionUID = 9169812926218561703L;

  private Integer code;
  @JsonProperty("msg")
  private String message;
  private T data;
}
