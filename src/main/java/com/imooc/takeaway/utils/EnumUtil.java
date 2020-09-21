package com.imooc.takeaway.utils;

import com.imooc.takeaway.enums.StatusEnum;

public class EnumUtil {
  public static <T extends StatusEnum> T getByCode(Integer code, Class<T> statusEnumClass) {
    for (T enumConstant : statusEnumClass.getEnumConstants()) {
      if(enumConstant.getCode().compareTo(code) == 0)
        return enumConstant;
    }
    return null;
  }
}
