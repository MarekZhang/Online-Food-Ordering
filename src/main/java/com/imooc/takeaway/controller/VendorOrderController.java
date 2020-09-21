package com.imooc.takeaway.controller;

import com.imooc.takeaway.dto.OrderDTO;
import com.imooc.takeaway.enums.ExceptionEnum;
import com.imooc.takeaway.service.OrderService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/vendor/order")
class VendorOrderController {

  @Resource(name = "OrderService")
  OrderService orderService;

  /**
   * list all orders
   * @param page
   * @param size
   * @param map
   * @return
   */
  @GetMapping("/list")
  public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                           Map<String, Object> map) {
    //page number start from 0;
    PageRequest pageRequest = new PageRequest(page - 1, size);
    Page<OrderDTO> orderList = orderService.findAll(pageRequest);
    map.put("orderList", orderList);
    map.put("page", page);
    map.put("size", size);
    return new ModelAndView("vendor/list", map);
  }

  /**
   * cancel order with given orderId
   * @param orderId
   * @param map
   * @return
   */
  @GetMapping("/cancel")
  public ModelAndView cancel(@RequestParam("orderId") String orderId,
                             Map<String, Object> map) {
    try {
      OrderDTO orderDTO = orderService.findOne(orderId);
      orderService.cancel(orderDTO);

    } catch (Exception e) {
      log.error("[vendor cancel order] cancel order error {}", e.getMessage());
      e.printStackTrace();
      map.put("msg", e.getMessage());
      map.put("url", "/sell/vendor/order/list");

      return new ModelAndView("common/error", map);
    }

    map.put("msg", ExceptionEnum.ORDER_CANCELLED_SUCCESSFULLY.getMsg());
    map.put("url", "/sell/vendor/order/list");

    return new ModelAndView("common/success", map);
  }

  /**
   * list order details with given orderId
   * @param orderId
   * @param map
   * @return
   */
  @GetMapping("/detail")
  public ModelAndView detail(@RequestParam("orderId") String orderId,
                             Map<String, Object> map) {
    OrderDTO orderDTO = null;

    try {
      orderDTO = orderService.findOne(orderId);
    } catch (Exception e) {
      log.error("[vendor check order detail] check order error {}", e.getMessage());
      e.printStackTrace();
      map.put("msg", e.getMessage());
      map.put("url", "/sell/vendor/order/list");
      e.printStackTrace();
      return new ModelAndView("common/error", map);
    }

    map.put("orderDTO", orderDTO);

    return new ModelAndView("vendor/detail", map);
  }

  /**
   * complete order with given orderId
   * @param orderId
   * @param map
   * @return
   */
  @GetMapping("/complete")
  public ModelAndView complete(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
    try {
      OrderDTO orderDTO = orderService.findOne(orderId);
      orderService.complete(orderDTO);
    } catch (Exception e) {
      log.error("[vendor complete order] complete order error {}", e.getMessage());
      e.printStackTrace();
      map.put("msg", e.getMessage());
      map.put("url", "/sell/vendor/order/list");

      return new ModelAndView("common/error", map);
    }

    map.put("msg", ExceptionEnum.ORDER_COMPLETE_SUCCESSFULLY.getMsg());
    map.put("url", "/sell/vendor/order/list");

    return new ModelAndView("common/success", map);

  }

}
