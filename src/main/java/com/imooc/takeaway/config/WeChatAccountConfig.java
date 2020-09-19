package com.imooc.takeaway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "weChat")//corresponds to the yaml config file
public class WeChatAccountConfig {
  private String appId;
  private String appsecret;
  /**
   * 商户号
   */
  private String mchId;

  /**
   * 商户密钥
   */
  private String mchKey;

  /**
   * 商户证书路径
   */
  private String keyPath;

  /**
   * 异步通知url
   */
  private String notifyUrl;
}
