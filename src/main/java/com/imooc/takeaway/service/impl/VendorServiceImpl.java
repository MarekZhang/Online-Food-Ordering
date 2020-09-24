package com.imooc.takeaway.service.impl;

import com.imooc.takeaway.domain.VendorInfo;
import com.imooc.takeaway.repository.VendorInfoRepository;
import com.imooc.takeaway.service.VendorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("VendorService")
public class VendorServiceImpl implements VendorService {
  @Autowired
  VendorInfoRepository vendorInfoRepository;

  @Override
  public VendorInfo findVendorInfoByOpenId(String openid) {
    return vendorInfoRepository.findByOpenid(openid);
  }

}
