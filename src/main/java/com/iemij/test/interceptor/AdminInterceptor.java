package com.iemij.test.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;
import com.iemij.test.common.Code;
import com.iemij.test.common.Response;
import com.iemij.test.controller.admin.AdminUserController;
import com.iemij.test.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserController.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = request.getSession().getId();
        RedisTemplate redisTemplate = (RedisTemplate) SpringBeanUtil.getBean("redisTemplate");
        Long uid = TypeUtils.castToLong(redisTemplate.opsForValue().get(sessionId));//从redis获取存储的用户id
        if (uid == null) {
            callback(response,Code.NEED_LOGIN);
            return false;
        }
        boolean isExpire = isExpire(redisTemplate,sessionId);
        if (isExpire) {
            callback(response,Code.LOGIN_EXPIRE);
            return false;
        }
        request.setAttribute("uid", uid);
        return true;
    }

    private void callback(HttpServletResponse response,Code code) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSON.toJSONString(Response.instance(code)));
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     * 判断key是否过期
     * @param key
     * @return
     */
    private boolean isExpire(RedisTemplate redisTemplate,String key) {
        return expire(redisTemplate,key) > 1?false:true;
    }


    /**
     * 从redis中获取key对应的过期时间;
     * 如果该值有过期时间，就返回相应的过期时间;
     * 如果该值没有设置过期时间，就返回-1;
     * 如果没有该值，就返回-2;
     * @param key
     * @return
     */
    private long expire(RedisTemplate redisTemplate,String key) {
        return redisTemplate.opsForValue().getOperations().getExpire(key);
    }
}
