package com.imooc.takeaway.form;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.Stack;

import lombok.Data;

@Data
public class OrderForm {
  @NotEmpty(message = "buyer name must not be empty")
  private String name;

  @NotEmpty(message = "buyer phone number must not be empty")
  private String phone;

  @NotEmpty(message = "buyer address must not be empty")
  private String address;

  @NotEmpty(message = "buyer wechat id must not be empty")
  private String openid;

  @NotEmpty(message = "shopping cart must not be empty")
  private String items;

}
