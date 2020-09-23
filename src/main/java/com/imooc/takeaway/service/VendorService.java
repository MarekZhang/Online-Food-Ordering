package com.imooc.takeaway.service;

import com.imooc.takeaway.domain.VendorInfo;

public interface VendorService {
  VendorInfo findVendorInfoByOpenId(String openid);
}
