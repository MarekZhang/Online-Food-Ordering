package com.imooc.takeaway.controller;

import com.imooc.takeaway.config.WeChatMPConfig;
import com.imooc.takeaway.enums.ExceptionEnum;
import com.imooc.takeaway.exception.OrderException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.service.WxService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {
  @Autowired
  WxMpService wxMpService;

  /**
   * construct the url used for weChat authorization;构造出用户点击跳出授权的url open.weixin.qq....
   * returnURL 是用来在userInfo方法执行完获得了openid之后跳转的地址，authorize和userinfo对用户是透明的，两个方法的断点是用户同意授权的前后
   * returnURL 传递到buildAuthorizationURL中的state参数，这样state在跳转到userInfo方法之后也是可以获取的
   * @param returnURL
   * @return String
   */
  @GetMapping("/authorize")
  public String authorize(@RequestParam("returnUrl") String returnURL) {
    String redirectURL = "http://markzhang.natapp1.cc/sell/wechat/userInfo";//得到accesstoken之后backend内部跳转的下一结点以获取openid
    String builtURL = wxMpService.getOAuth2Service().buildAuthorizationUrl(redirectURL, WxConsts.OAuth2Scope.SNSAPI_BASE, returnURL);

    return "redirect:" + builtURL;
  }

  @GetMapping("/userInfo")
  public String userInfo(@RequestParam("code") String code, @RequestParam("state") String returnURL){
    WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();

    try {
      wxMpOAuth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(code);

    } catch (WxErrorException e) {
      log.error("[weChat mp service authorization error] {}", e);
      throw new OrderException(ExceptionEnum.WECHAT_MPSERVICE_ERROR.getCode(), e.getError().getErrorMsg());
    }

    String openId = wxMpOAuth2AccessToken.getOpenId();

    return "redirect:" + returnURL + "?openid=" + openId ;
  }
}
