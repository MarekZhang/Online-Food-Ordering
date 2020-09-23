package com.imooc.takeaway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "weChatUrl")
public class WeChatURLConfig {
  private String serverUrl;

  private String openAuthRedirectUrl;

  private String projectUrl;
}
