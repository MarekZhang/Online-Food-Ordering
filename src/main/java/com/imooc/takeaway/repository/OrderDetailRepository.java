package com.imooc.takeaway.repository;

import com.imooc.takeaway.domain.OrderDetail;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
  List<OrderDetail> findByOrderId(String orderId);
}
