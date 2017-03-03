package com.jy.activiti.helper;

import com.jy.activiti.common.util.StringUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class LoginHelper {

    private static final String SESSION_KEY = "login_session";
    private static final String USER_NAME_COOKIE_KEY = "username_key";

    @Autowired
    private CookieHelper cookieHelper;
    @Autowired
    private IdentityService identityService;

    private final Map<String, User> sessionHolder = new HashMap<>();

    /**
     * 创建session
     * */
    public String createSession(User user, HttpServletResponse response) {
        String key = UUID.randomUUID().toString();
        sessionHolder.put(key, user);
        if (response != null) {
            cookieHelper.addCookie(response, SESSION_KEY, key);
            cookieHelper.addCookie(response, USER_NAME_COOKIE_KEY, user.getId().toString());
        }
        return key;
    }

    public User getSessionCacheUser(String key) {
        return sessionHolder.get(key);
    }

    public User getSessionCacheUser(HttpServletRequest request) {
        String sessionKey = cookieHelper.getCookieValue(request, SESSION_KEY);
        if (StringUtil.isEmpty(sessionKey)) {
            return null;
        }
        return sessionHolder.get(sessionKey);
    }

    public User login(String username, String password, String code) {
        User user = identityService.createUserQuery().userId(username).singleResult();
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String sessionKey = cookieHelper.getCookieValue(request, SESSION_KEY);
        //清楚缓存用户
        if (!StringUtil.isEmpty(sessionKey)) {
            sessionHolder.remove(sessionKey);
        }
        //清除cookie
        Cookie cookie = new Cookie(SESSION_KEY, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

}
