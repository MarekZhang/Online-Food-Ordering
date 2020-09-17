package com.imooc.takeaway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "weChat")//corresponds to the yaml config file
public class WeChatAccountConfig {
  String appId;
  String appsecret;
}
