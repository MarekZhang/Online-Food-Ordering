package com.imooc.takeaway.service.impl;

import com.imooc.takeaway.domain.VendorInfo;
import com.imooc.takeaway.service.VendorService;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VendorServiceImplTest extends TestCase {
  @Resource(name = "VendorService")
  VendorService vendorService;

  private final static String OPENID = "asdasfa";

  @Test
  public void findVendorInfoByOpenId() {
    VendorInfo vendorInfo = vendorService.findVendorInfoByOpenId(OPENID);
    Assert.assertEquals(OPENID, vendorInfo.getOpenid());

  }
}