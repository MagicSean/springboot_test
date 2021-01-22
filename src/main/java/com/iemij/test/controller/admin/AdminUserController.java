package com.iemij.test.controller.admin;

import cn.hutool.core.convert.Convert;
import com.iemij.test.common.Constants;
import com.iemij.test.common.Response;
import com.iemij.test.common.RedisKey;
import com.iemij.test.controller.BaseController;
import com.iemij.test.domain.CommonUser;
import com.iemij.test.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController("adminUserController")
@RequestMapping("/admin/user")
public class AdminUserController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserController.class);
    private static final String tags = "后台管理用户相关接口";
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/login")
    @ApiOperation(value = "登录", httpMethod = "GET", tags = {tags})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "account", value = "账号", required = true, dataType = "String", defaultValue = "15543589860"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String", defaultValue = "q1192883638"),
    })
    public Response login(HttpServletRequest request, String account, String password) {
        try {
            CommonUser commonUser = userService.adminLogin(account, password);
            Map<String, Object> claims = new HashMap<>();
            claims.put("uid", commonUser.getUid());
            String redisKey = RedisKey.uid(request.getSession().getId());
            redisTemplate.opsForValue().set(redisKey, Convert.toStr(commonUser.getUid()));//将用户id存入redis
            redisTemplate.expire(redisKey, Constants.ADMIN_TOKEN_VALIDITY, TimeUnit.MILLISECONDS);//设置有效期
            return Response.succInstance(commonUser);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.errInstance(e.getMessage());
        }
    }

    /**
     * 由于login时，通过redis，将sessionId，uid存储，这里可以不传uid，而是在AdminInterceptor里面从redis里面获取uid
     * @param request
     * @return
     */
    @RequestMapping("/getUserInfo")
    @ApiOperation(value = "获取用户信息", httpMethod = "GET", tags = {tags})
    public Response getUserInfo(HttpServletRequest request) {
        try {
            Long uid = Convert.toLong(request.getAttribute("uid"));
            return Response.succInstance(userService.getUserInfo(uid));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.errInstance(e.getMessage());
        }
    }
}
