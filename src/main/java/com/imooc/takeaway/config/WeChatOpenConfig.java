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
    wxMpService.setWxMpConfigStorage(wxMpConfigStorage());

    return wxMpService;
  }

  @Bean
  public WxMpConfigStorage wxMpConfigStorage(){
    WxMpDefaultConfigImpl wxMpDefaultConfig = new WxMpDefaultConfigImpl();
    wxMpDefaultConfig.setAppId(weChatAccountConfig.getOpenAppId());
    wxMpDefaultConfig.setSecret(weChatAccountConfig.getOpenAppSecret());

    return wxMpDefaultConfig;
  }
}
