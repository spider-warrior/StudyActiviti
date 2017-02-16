package com.jy.activiti.helper;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class CookieHelper {

    public CookieHelper addCookie(HttpServletResponse response, String name, String value) {
        response.addCookie(new Cookie(name, value));
        return this;
    }

    private CookieHelper addCookie(HttpServletResponse response, Map<String, String> cookies) {
        for (Map.Entry<String, String> entry: cookies.entrySet()) {
            response.addCookie(new Cookie(entry.getKey(), entry.getValue()));
        }
        return this;
    }

    public String getCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c: cookies) {
                if (c.getName().equals(key)) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

}
