package com.imooc.takeaway.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
  /**
   * set cookie with given expiry
   * @param response
   * @param name
   * @param value
   * @param expiry
   */
  public static void setCookie(HttpServletResponse response, String name, String value, Integer expiry) {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    cookie.setMaxAge(expiry);
    response.addCookie(cookie);
  }

  /**
   * get cookie(named token)
   *
   * @param request
   * @param cookieName
   * @return
   */
  public static Cookie getCookie(HttpServletRequest request, String cookieName) {

    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equalsIgnoreCase(cookieName)) {
        return cookie;
      }
    }
    return null;
  }
}
