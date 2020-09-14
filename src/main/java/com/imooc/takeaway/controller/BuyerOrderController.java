package com.imooc.takeaway.controller;

import com.imooc.takeaway.converter.OrderForm2OrderDTO;
import com.imooc.takeaway.domain.OrderDetail;
import com.imooc.takeaway.dto.OrderDTO;
import com.imooc.takeaway.enums.ExceptionEnum;
import com.imooc.takeaway.exception.OrderException;
import com.imooc.takeaway.form.OrderForm;
import com.imooc.takeaway.service.BuyerService;
import com.imooc.takeaway.service.OrderService;
import com.imooc.takeaway.utils.ResultVOUtil;
import com.imooc.takeaway.viewObject.ResultVO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
  @Resource(name = "OrderService")
  OrderService orderService;

  @Resource(name = "BuyerService")
  BuyerService buyerService;

  //1.create order
  @PostMapping("/create")
  public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      log.error("[Create order] Invalid form params orderForm={}", orderForm);
      throw new OrderException(ExceptionEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
    }

    OrderDTO orderDto = OrderForm2OrderDTO.convert(orderForm);
    if (orderDto.getOrderDetailList().isEmpty()) {
      log.error("[Create order] order list cannot be empty orderForm={}", orderForm);
      throw new OrderException(ExceptionEnum.CART_EMPTY_ERROR);
    }
    Map<String, String> resultMap = new HashMap<>();
    OrderDTO resultDTO = orderService.create(orderDto);
    resultMap.put("orderId", resultDTO.getOrderId());

    return ResultVOUtil.success(resultMap);
  }

  //2.order list
  @GetMapping("/list")
  public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                       @RequestParam(value = "page", defaultValue = "0") Integer page,
                                       @RequestParam(value = "size", defaultValue = "10") Integer size) {
    if (openid == null) {
      log.error("[list order] openId must not be empty");
      throw new OrderException(ExceptionEnum.PARAM_ERROR);
    }
    PageRequest pageRequest = new PageRequest(page, size);
    Page<OrderDTO> pages = orderService.findList(openid, pageRequest);

    return ResultVOUtil.success(pages.getContent());
  }

  //3. order detail
  @GetMapping("/detail")
  public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
    OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
    return ResultVOUtil.success(orderDTO);
  }

  //4.cancel order
  @PostMapping("/cancel")
  public ResultVO cancel(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
    buyerService.cancelOrder(openid, orderId);

    return ResultVOUtil.success();
  }
}
