package com.imooc.takeaway.handler;

import com.imooc.takeaway.config.WeChatURLConfig;
import com.imooc.takeaway.exception.AuthorizationException;
import com.imooc.takeaway.exception.OrderException;
import com.imooc.takeaway.utils.ResultVOUtil;
import com.imooc.takeaway.viewObject.ResultVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
  @Autowired
  WeChatURLConfig weChatURLConfig;

  @ExceptionHandler(value = AuthorizationException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ModelAndView authorizationHandler() {
    return new ModelAndView("redirect:" +
            weChatURLConfig.getProjectUrl() +
            "/sell/wechat/qrAuthorize?returnUrl=" +
            weChatURLConfig.getQrAuthorizationReturnUrl());
  }

  @ExceptionHandler(value = OrderException.class)
  @ResponseBody
  public ResultVO handelProductException(OrderException e) {
    return ResultVOUtil.error(e.getCode(), e.getMessage());
  }
}
