package com.imooc.takeaway.service.impl;

import com.imooc.takeaway.exception.OrderException;
import com.imooc.takeaway.service.SecKillService;
import com.imooc.takeaway.utils.KeyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Service("SecKillService")
@Slf4j
public class SecKillServiceImpl implements SecKillService {
  @Resource(name = "RedisLock")
  RedisLock redisLock;

  private static final int TIMEOUT = 5 * 1000;

  static Map<String,Integer> products;
  static Map<String,Integer> stock;
  static Map<String,String> orders;
  static
  {
    products = new HashMap<>();
    stock = new HashMap<>();
    orders = new HashMap<>();
    products.put("123456", 100000);
    stock.put("123456", 100000);
  }

  private String queryMap(String productId)
  {
    return "SecKill Activity  [Quantity of productX: "
            + products.get(productId) + " ]"
            +"   [remaining：" + stock.get(productId)+" pieces ]"
            +"   [number of users purchased successfully："
            +  orders.size() +" ]";
  }

  @Override
  public String querySecKillProductInfo(String productId)
  {
    return this.queryMap(productId);
  }

  @Override
  public void orderProductMockDiffUser(String productId)
  {
    //lock
    String value = String.valueOf(System.currentTimeMillis() + TIMEOUT);
    if (!redisLock.lock(productId, value)) {
      log.warn("[SecKill Activity] number of participants exceeds expectation");
      return;
    }

    //1.query product stock, if stockNum == 0
    int stockNum = stock.get(productId);
    if(stockNum == 0) {
      throw new OrderException(100,"out of stock");
    }else {
      //2.create order with UID
      orders.put(KeyUtil.getUID(),productId);
      //3.reduce the stock
      stockNum =stockNum-1;
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      stock.put(productId,stockNum);
    }

    //unlock
    redisLock.unlock(productId, value);
  }
}