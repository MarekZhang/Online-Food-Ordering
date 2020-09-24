package com.imooc.takeaway.service.impl;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@ServerEndpoint("/webSocket")
public class WebSocket {
  private Session session;
  /**
   * thread safe set
   */
  private static Set<WebSocket> listeners = new CopyOnWriteArraySet<>();

  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    listeners.add(this);
    log.info("[web socket message] set up a connection, number of connections: {}", listeners.size());
  }

  @OnClose
  public void onClose(Session session) {
    listeners.remove(this);
    log.info("[web socket message] close a connection, number of connections: {}", listeners.size());
  }

  /**
   * server side receive message from the client side
   * @param message
   */
  @OnMessage
  public void onMessage(String message) {
    log.info("[web socket message] receive message from the client side={}", message);
  }

  /**
   * server broadcast message to front end sockets
   * @param message
   */
  public void broadcast(String message) {
    for (WebSocket listener : listeners) {
      log.info("[web socket message] broadcast message={}", message);
      try {
        listener.session.getBasicRemote().sendText(message);
      } catch (IOException e) {
        log.error("[web socket message] broadcast message error: {}", e.getMessage());
      }
    }
  }

}
