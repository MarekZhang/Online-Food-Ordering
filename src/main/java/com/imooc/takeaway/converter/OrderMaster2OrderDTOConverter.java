package com.imooc.takeaway.converter;

import com.imooc.takeaway.domain.OrderMaster;
import com.imooc.takeaway.dto.OrderDTO;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTOConverter {

  public static OrderDTO convert(OrderMaster orderMaster){
    OrderDTO dto = new OrderDTO();
    BeanUtils.copyProperties(orderMaster, dto);
    return dto;
  }

  public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
    List<OrderDTO> orderDTOList = orderMasterList.stream().map(e -> convert(e))
            .collect(Collectors.toList());

    return orderDTOList;
  }
}
