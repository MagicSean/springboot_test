package com.iemij.test.controller.app;

import com.iemij.test.common.Constants;
import com.iemij.test.common.Response;
import com.iemij.test.controller.BaseController;
import com.iemij.test.service.UserService;
import com.iemij.test.util.TokenUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController("appUserController")
@RequestMapping("/app/user")
public class AppUserController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppUserController.class);
    private static final String tags = "App端用户相关接口";
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ApiOperation(value = "登录", httpMethod = "GET", tags = {tags})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "account", value = "账号", required = true, dataType = "String", defaultValue = "15543589860"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String", defaultValue = "q1192883638"),
    })
    public Response login(String account, String password) {
        try {
            Long uid = userService.login(account, password);
            Map<String, Object> claims = new HashMap<>();
            claims.put("uid", uid);
            String token = TokenUtil.create(Constants.TOKEN_VALIDITY, claims);
            return Response.succInstance(token);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.errInstance();
        }
    }
}
