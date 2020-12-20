package com.iemij.test.util;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtil {

    /**
     * 判断key是否过期
     * @param key
     * @return
     */
    public static boolean isExpire(RedisTemplate redisTemplate, String key) {
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
    public static long expire(RedisTemplate redisTemplate,String key) {
        return redisTemplate.opsForValue().getOperations().getExpire(key);
    }

}
