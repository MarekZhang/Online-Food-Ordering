package com.imooc.takeaway.repository;

import com.imooc.takeaway.domain.VendorInfo;
import com.imooc.takeaway.utils.KeyUtil;
import com.sun.org.apache.xml.internal.security.keys.KeyUtils;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VendorInfoRepositoryTest extends TestCase {
  @Autowired
  VendorInfoRepository repository;

  @Test
  public void save() {
    VendorInfo vendorInfo = new VendorInfo();
    vendorInfo.setVendorId(KeyUtil.getUID());
    vendorInfo.setUsername("mark");
    vendorInfo.setPassword("123456");
    vendorInfo.setOpenid("asdasfa");

    VendorInfo res = repository.save(vendorInfo);

    Assert.assertNotNull(res);
  }

  @Test
  public void findByVendorId() {
    VendorInfo res = repository.findByOpenid("asdasfa");
    Assert.assertNotNull(res);
  }

}