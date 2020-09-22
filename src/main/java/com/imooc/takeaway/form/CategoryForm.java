package com.imooc.takeaway.form;

import java.util.Date;

import lombok.Data;

@Data
public class CategoryForm {

  private Integer categoryId;

  private String categoryName;

  private Integer categoryType;

  private Integer prevCategoryType;
}
