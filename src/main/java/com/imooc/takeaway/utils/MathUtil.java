package com.imooc.takeaway.utils;

public class MathUtil {
  private final static double PRECISION = 0.01;

  public static boolean amountVerification(double amount1, double amount2) {
    return Math.abs(amount1 - amount2) < PRECISION;
  }
}
