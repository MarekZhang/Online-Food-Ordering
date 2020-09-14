package com.imooc.takeaway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {
  public final Logger logger = LoggerFactory.getLogger(LoggerTest.class);
  @Test
  public void test1() {
    String name = "Mark";
    String password = "1111";
    logger.info("info..");
    logger.error("error..");
    logger.debug("debug..");

    logger.warn("name:{} , password:{}", name, password);
  }
}
