package com.imooc.takeaway.aop;

import com.imooc.takeaway.constants.CookieConstant;
import com.imooc.takeaway.constants.RedisConstants;
import com.imooc.takeaway.exception.AuthorizationException;
import com.imooc.takeaway.utils.CookieUtil;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class VendorLoginAuthorization {
  @Autowired
  StringRedisTemplate redisTemplate;

  @Pointcut(value = "execution(* com.imooc.takeaway.controller.Vendor*.*(..))" +
          "&& !execution(* com.imooc.takeaway.controller.VendorUserController.*(..))")
  private void authorize() {}

  @Before(value="authorize()")
  public void authorization() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
    if (cookie == null) {
      log.warn("[log authorization] cannot find token in cookie");
      throw new AuthorizationException();
    }
    String redisValue = redisTemplate.opsForValue().get(String.format(RedisConstants.TOKEN_PREFIX, cookie.getValue()));
    if (redisValue == null || redisValue.length() == 0) {
      log.warn("[log authorization] cannot find cookie in redis");
      throw new AuthorizationException();
    }
  }
}
