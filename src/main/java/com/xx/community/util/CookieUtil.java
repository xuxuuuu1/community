package com.xx.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {

    /**
     * 遍历游览器的cookie 如果浏览器中有cookie与想找的cookie相同的cookie，则返回value
     * @param request
     * @param name
     * @return
     */
    public static String getValue(HttpServletRequest request,String name) {
        if (request == null || name == null) {
            throw new IllegalArgumentException("参数不能为空");
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
