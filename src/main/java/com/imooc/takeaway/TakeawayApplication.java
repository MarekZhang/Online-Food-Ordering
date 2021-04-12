package com.imooc.takeaway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TakeawayApplication {

  public static void main(String[] args) {
    SpringApplication.run(TakeawayApplication.class, args);
  }

}
