package com.mhdss.ggs.interceptor;

import com.mhdss.ggs.dataobject.WxUserDO;
import com.mhdss.ggs.dto.AuthAgent;
import com.mhdss.ggs.exception.NotLoginException;
import com.mhdss.ggs.service.WxUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@ConfigurationProperties(prefix = "dc.auth")
public class Interceptor implements InitializingBean, HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(Interceptor.class);
    private static final String SESSION_KEY_NAME = "sessionKey";

    @Autowired
    private AuthAgent authAgent;

    private String[] includes;

    @Autowired
    private WxUserService wxUserService;

    @Override
    public void afterPropertiesSet() {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("preHandle==================== {}");


        String ip = getLocalIp(request);
        authAgent.setIp(ip);
        String sessionKey = request.getHeader(SESSION_KEY_NAME);
        WxUserDO wxUserDO = wxUserService.queryUserBySession(sessionKey);
        if (null != wxUserDO) {
            authAgent.setWxUserId(wxUserDO.getId());
        } else {
            throw new NotLoginException("", "");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }


    public String[] getIncludes() {
        return includes;
    }

    public void setIncludes(String[] includes) {
        this.includes = includes;
    }


    public static String getLocalIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if (forwarded != null) {
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip;
    }
}
