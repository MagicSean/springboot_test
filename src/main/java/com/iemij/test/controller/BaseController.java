package com.iemij.test.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.util.TypeUtils;
import com.iemij.test.util.TokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseController {

    /**
     * 当前端传来的时间参数是字符串，而参数对象中的对应字段是Date类型时，自动转换
     * @param binder
     */
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//    }

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
