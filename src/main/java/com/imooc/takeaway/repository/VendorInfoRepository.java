package com.imooc.takeaway.repository;

import com.imooc.takeaway.domain.VendorInfo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorInfoRepository extends JpaRepository<VendorInfo, String> {
  VendorInfo findByOpenid(String Openid);
}

