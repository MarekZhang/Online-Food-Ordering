package com.imooc.takeaway.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class VendorInfo {
  @Id
  private String vendorId;

  private String username;

  private String password;

  private String openid;

  private Date createTime;

  private Date updateTime;

}
