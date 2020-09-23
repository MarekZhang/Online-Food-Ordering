package com.imooc.takeaway.controller;

import com.imooc.takeaway.config.WeChatOpenConfig;
import com.imooc.takeaway.config.WeChatURLConfig;
import com.imooc.takeaway.constants.CookieConstant;
import com.imooc.takeaway.constants.RedisConstants;
import com.imooc.takeaway.domain.VendorInfo;
import com.imooc.takeaway.enums.ExceptionEnum;
import com.imooc.takeaway.service.VendorService;
import com.imooc.takeaway.utils.CookieUtil;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/vendor")
public class VendorUserController {
  @Resource(name = "VendorService")
  private VendorService vendorService;

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  private WeChatURLConfig weChatURLConfig;

  /**
   * authorization verification
   *
   * @param openid
   * @param map
   * @return
   */
  @GetMapping("/login")
  public ModelAndView login(@RequestParam("openid") String openid,
                            Map<String, Object> map,
                            HttpServletResponse response) {
    //1. retrieve user with given openid, authorization verification
    VendorInfo vendorInfo = vendorService.findVendorInfoByOpenId(openid);
    if (vendorInfo == null) {
      map.put("msg", ExceptionEnum.UNAUTHORIZED_VENDOR_ERROR.getMsg());
      map.put("url", "/sell/vendor/order/list");
      return new ModelAndView("common/error", map);
    }

    //2. put {token: openid} pair into redis with 30 minutes expiry
    String token = UUID.randomUUID().toString();
    Integer expiry = RedisConstants.EXPIRY;
    redisTemplate.opsForValue().set(String.format(RedisConstants.TOKEN_PREFIX, token), openid, expiry, TimeUnit.SECONDS);

    //3. put token into browser cookie
    CookieUtil.setCookie(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRY);

    return new ModelAndView("redirect:" + weChatURLConfig.getProjectUrl() + "/sell/vendor/order/list");
  }

  @GetMapping("/logout")
  public ModelAndView logout(HttpServletRequest request,
                             HttpServletResponse response,
                             Map<String, Object> map) {
    Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
    if (cookie != null) {
      //1.delete cookie pair in redis
      redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstants.TOKEN_PREFIX, cookie.getValue()));

      //2.clear the cookie in the web browser, by setting the expiry as 0
      CookieUtil.setCookie(response, CookieConstant.TOKEN, null, 0);
    }
    map.put("msg", ExceptionEnum.SUCCESSFULLY_LOGOUT.getMsg());
    map.put("url", "/sell/vendor/order/list");

    return new ModelAndView("common/success", map);
  }
}
