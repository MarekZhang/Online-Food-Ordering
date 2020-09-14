package com.imooc.takeaway.utils;

import java.util.Random;

public class KeyUtil {
  public static synchronized String getUID() {
    Random random = new Random();
    Integer num = random.nextInt(900000) + 100000;

    return System.currentTimeMillis() + "" + num;
  }

}
