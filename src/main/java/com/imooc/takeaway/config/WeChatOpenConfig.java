package com.imooc.takeaway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;

@Configuration
public class WeChatOpenConfig {
  @Autowired
  WeChatAccountConfig weChatAccountConfig;

  @Bean
  public WxMpService wxOpenService() {
    WxMpService wxMpService = new WxMpServiceImpl();
    wxMpService.setWxMpConfigStorage(openWxMpConfigStorage());

    return wxMpService;
  }

  /**
   * @Bean 根据函数名称配置bean名称，此处在openWxMpConfigStorage前加了open是为了与WeChatAccountConfig做授权部分
   * 的wxMpConfigStorage配置区分开，否则授权部分会加载appId为openappId
   * @return
   */
  @Bean
  public WxMpConfigStorage openWxMpConfigStorage(){
    WxMpDefaultConfigImpl wxMpDefaultConfig = new WxMpDefaultConfigImpl();
    wxMpDefaultConfig.setAppId(weChatAccountConfig.getOpenAppId());
    wxMpDefaultConfig.setSecret(weChatAccountConfig.getOpenAppSecret());

    return wxMpDefaultConfig;
  }
}
