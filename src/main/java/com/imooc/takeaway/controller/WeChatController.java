package com.imooc.takeaway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/weChat")
@Slf4j
public class WeChatController {

  @GetMapping("/auth")
  public void auth(){
    log.info("");

  }
}
