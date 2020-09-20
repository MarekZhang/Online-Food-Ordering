package com.imooc.takeaway.controller;

import com.imooc.takeaway.dto.OrderDTO;
import com.imooc.takeaway.enums.ExceptionEnum;
import com.imooc.takeaway.exception.OrderException;
import com.imooc.takeaway.service.OrderService;
import com.imooc.takeaway.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {
  @Resource(name = "OrderService")
  OrderService orderService;

  @Resource(name = "PayService")
  PayService payService;

  /**
   * create payment proxy url http://proxy.springboot.cn/pay?openid=${OPENID}&orderId=${ORDER_ID}&returnUrl=${RETURN_URL}
   *
   * @param orderId
   * @param returnUrl
   * @param map
   * @return
   */
  @GetMapping("/create")
  public ModelAndView create(@RequestParam("orderId") String orderId,
                             @RequestParam("returnUrl") String returnUrl,
                             Map<String, Object> map) {
    //1. find the order
    OrderDTO order = orderService.findOne(orderId);
    if (order == null)
      throw new OrderException(ExceptionEnum.ORDER_NOT_EXIST);

    //2. pay the order
    PayResponse payResponse = payService.create(order);

    map.put("payResponse", payResponse);
    returnUrl = returnUrl + "/#/order/" + orderId;
    try {
      map.put("returnUrl", URLDecoder.decode(returnUrl, StandardCharsets.UTF_8.name()));
    } catch (UnsupportedEncodingException e) {
      log.error("URL decoding error{}", e);
      e.printStackTrace();
    }

    //jump to the freemarker file built in resource/templates/pay/create.ftl
    return new ModelAndView("pay/create", map);

  }

  @GetMapping("/proxy")
  public String proxy(@RequestParam("orderId") String orderId,
                      @RequestParam("returnUrl") String returnUrl) {

    String baseUrl = "http://proxy.springboot.cn/pay?openid=oTgZpwSGwQ-uDrwwjzVvZyllY80o";

    String decodeUrl = "";

    try {
      decodeUrl = URLDecoder.decode(returnUrl, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    String redirectUrl = baseUrl + "&orderId=" + orderId + "&returnUrl=" + decodeUrl;
    log.info("proxy url is: {}", redirectUrl);
    return "redirect:" + redirectUrl;
  }

  /**
   * payment process https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_4 step 11, return
   * payment result to weChat platform
   * <xml>
   * <return_code><![CDATA[SUCCESS]]></return_code>
   * <return_msg><![CDATA[OK]]></return_msg>
   * </xml>
   *
   * @param notifyData
   * @return
   */
  @PostMapping("/notify")
  public ModelAndView notify(@RequestBody String notifyData) {
    PayResponse payResponse = payService.notify(notifyData);

    return new ModelAndView("pay/success");
  }
}
