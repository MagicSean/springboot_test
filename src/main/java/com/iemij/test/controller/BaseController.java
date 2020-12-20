package com.iemij.test.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.util.TypeUtils;
import com.iemij.test.util.TokenUtil;
import io.jsonwebtoken.Claims;

public class BaseController {
    public Long getUidFromToken(String token) throws Exception {
        try {
            Long uid = null;
            if (StrUtil.isNotEmpty(token)) {
                Claims claims = TokenUtil.parse(token);
                uid = TypeUtils.castToLong(claims.get("uid"));
            }
            return uid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
