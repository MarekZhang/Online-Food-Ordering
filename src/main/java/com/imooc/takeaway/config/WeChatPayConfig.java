package com.imooc.takeaway.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WeChatPayConfig {
  @Autowired
  WeChatAccountConfig weChatAccountConfig;

  @Bean
  public BestPayServiceImpl bestPayService(){
    WxPayH5Config wxPayH5Config = new WxPayH5Config();
    //授权与支付id分离
    wxPayH5Config.setAppId("wxd898fcb01713c658");//借用账号appId
    wxPayH5Config.setAppSecret("8b7480add5af214ee585e9eed6f9a73f");//借用账号appSecret
    wxPayH5Config.setMchId(weChatAccountConfig.getMchId());
    wxPayH5Config.setMchKey(weChatAccountConfig.getMchKey());
    wxPayH5Config.setKeyPath(weChatAccountConfig.getKeyPath());
    wxPayH5Config.setNotifyUrl(weChatAccountConfig.getNotifyUrl());

    BestPayServiceImpl bestPayService = new BestPayServiceImpl();
    bestPayService.setWxPayH5Config(wxPayH5Config);

    return bestPayService;
  }
}
