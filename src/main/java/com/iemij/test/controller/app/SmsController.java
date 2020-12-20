package com.iemij.test.controller.app;

import cn.hutool.core.util.RandomUtil;
import com.iemij.test.common.Code;
import com.iemij.test.common.Constants;
import com.iemij.test.common.Response;
import com.iemij.test.config.RedisKey;
import com.iemij.test.controller.BaseController;
import com.iemij.test.util.RedisUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/app/sms")
public class SmsController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsController.class);
    private static final String tags = "App端短信相关接口";

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/send-verify")
    @ApiOperation(value = "发送验证码", httpMethod = "GET", tags = {tags})
    @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true, dataType = "String", defaultValue = "15543589860")
    public Response sendVerify(@RequestParam  String phone) {
        try {
            String verifyCode = RandomUtil.randomNumbers(6);
            String redisKey = RedisKey.verifyCode(phone);
            redisTemplate.opsForValue().set(redisKey,verifyCode);
            redisTemplate.expire(redisKey, Constants.VERIFY_CODE_VALIDITY, TimeUnit.MILLISECONDS);
            return Response.succInstance(verifyCode);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.errInstance(e.getMessage());
        }
    }

    @RequestMapping("/verify-phone")
    @ApiOperation(value = "校验手机验证码", httpMethod = "GET", tags = {tags})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true, dataType = "String", defaultValue = "15543589860"),
            @ApiImplicitParam(paramType = "query", name = "verifyCode", value = "验证码", required = true, dataType = "String"),
    })
    public Response verify(@RequestParam String phone, @RequestParam String verifyCode) {
        try {
            String code = (String) redisTemplate.opsForValue().get(RedisKey.verifyCode(phone));
            if (!verifyCode.equals(code)) {
                return Response.instance(Code.MATCH_VERIFY_ERR);
            }
            if (RedisUtil.isExpire(redisTemplate,RedisKey.verifyCode(phone))) {
                return Response.instance(Code.EXPIRE_VERIFY_ERR);
            }
            return Response.succInstance();
        } catch (Exception e) {
            LOGGER.info("msg=verifyPhoneErr", e);
            return Response.errInstance(e.getMessage());
        }
    }
}
