package com.imooc.takeaway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;

@Configuration
public class WeChatMPConfig {
  @Autowired
  WeChatAccountConfig weChatAccountConfig;
  @Bean
  public WxMpConfigStorage wxMpConfigStorage(){
    WxMpDefaultConfigImpl wxMpDefaultConfig = new WxMpDefaultConfigImpl();
    wxMpDefaultConfig.setAppId(weChatAccountConfig.getAppId());
    wxMpDefaultConfig.setSecret(weChatAccountConfig.getAppsecret());

    return wxMpDefaultConfig;
  }

  @Bean
  public WxMpService wxMpService(){
    WxMpService wxMpService = new WxMpServiceImpl();
    wxMpService.setWxMpConfigStorage(wxMpConfigStorage());

    return wxMpService;
  }

}
