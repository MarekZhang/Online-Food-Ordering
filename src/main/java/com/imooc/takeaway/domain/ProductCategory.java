package com.imooc.takeaway.domain;

import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
public class ProductCategory {

  @Id
  @GeneratedValue
  private Integer categoryId;

  private String categoryName;

  private Integer categoryType;

  private Date createTime;

  private Date updateTime;

}
