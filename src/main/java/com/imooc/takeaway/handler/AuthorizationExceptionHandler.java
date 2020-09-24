package com.imooc.takeaway.handler;

import com.imooc.takeaway.config.WeChatURLConfig;
import com.imooc.takeaway.exception.AuthorizationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AuthorizationExceptionHandler {
  @Autowired
  WeChatURLConfig weChatURLConfig;

  @ExceptionHandler(value = AuthorizationException.class)
  public ModelAndView authorizationHandler() {
    return new ModelAndView("redirect:" +
            weChatURLConfig.getProjectUrl() +
            "/sell/wechat/qrAuthorize?returnUrl=" +
            weChatURLConfig.getQrAuthorizationReturnUrl());
  }
}
