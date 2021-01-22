package com.iemij.test.controller.app;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iemij.test.common.Code;
import com.iemij.test.common.Constants;
import com.iemij.test.common.Response;
import com.iemij.test.common.RedisKey;
import com.iemij.test.controller.BaseController;
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
    public Response sendVerify(@RequestParam String phone) {
        try {
            String verifyCode = RandomUtil.randomNumbers(6);
            if (beforeResponseVerifyCode(phone,verifyCode)){
                return Response.succInstance(verifyCode);
            } else {
                return Response.instance(Code.SEND_VERIFY_TOO_OFEN_ERR);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.errInstance(e.getMessage());
        }
    }

    /**
     * 存储验证码数组到redis，防刷
     * @param phone
     * @param verifyCode
     * @return
     */
    private boolean beforeResponseVerifyCode(String phone,String verifyCode) {
        String redisKey = RedisKey.verifyCode(phone);
        JSONArray jsonArray = (JSONArray) JSON.parse((String)redisTemplate.opsForValue().get(redisKey));
        Long currentTime = System.currentTimeMillis();
        if (jsonArray == null) {
            jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sendTime",currentTime);
            jsonObject.put("verifyCode", verifyCode);
            jsonArray.add(jsonObject);
        } else {
            Long lastSendTime = jsonArray.getJSONObject(0).getLong("sendTime");
            int lastSendNum = jsonArray.size();
            Long timeScope = Constants.VERIFY_CODE_TIME_SCOPE;
            if(lastSendNum < Constants.VERIFY_CODE_NUM_SCOPE){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("sendTime",currentTime);
                jsonObject.put("verifyCode", verifyCode);
                jsonArray.add(jsonObject);
            } else if(currentTime - lastSendTime > timeScope && lastSendNum == Constants.VERIFY_CODE_NUM_SCOPE){
                for (int i = jsonArray.size()-1; i >= 0; i--) {
                    if (currentTime - jsonArray.getJSONObject(i).getLong("sendTime") > timeScope) {
                        jsonArray.remove(i);
                    }
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("sendTime",currentTime);
                jsonObject.put("verifyCode", verifyCode);
                jsonArray.add(jsonObject);
            } else if(currentTime - lastSendTime <= timeScope && lastSendNum >= Constants.VERIFY_CODE_NUM_SCOPE) {
                return false;
            }
        }
        redisTemplate.opsForValue().set(redisKey, jsonArray.toString());
        redisTemplate.expire(redisKey, Constants.VERIFY_DATA_VALIDITY, TimeUnit.HOURS);
        return true;
    }

    @RequestMapping("/verify-phone")
    @ApiOperation(value = "校验手机验证码", httpMethod = "GET", tags = {tags})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true, dataType = "String", defaultValue = "15543589860"),
            @ApiImplicitParam(paramType = "query", name = "verifyCode", value = "验证码", required = true, dataType = "String"),
    })
    public Response verify(@RequestParam String phone, @RequestParam String verifyCode) {
        try {
            JSONArray jsonArray = (JSONArray) JSON.parse((String)redisTemplate.opsForValue().get(RedisKey.verifyCode(phone)));
            if (jsonArray == null) {
                return Response.instance(Code.MATCH_VERIFY_ERR);
            }
            JSONObject lastSend = jsonArray.getJSONObject(jsonArray.size() - 1);
            if(System.currentTimeMillis() - lastSend.getLong("sendTime") > Constants.VERIFY_CODE_VALIDITY){
                return Response.instance(Code.EXPIRE_VERIFY_ERR);
            }
            if (!verifyCode.equals(lastSend.getString("verifyCode"))) {
                return Response.instance(Code.MATCH_VERIFY_ERR);
            }
            return Response.succInstance();
        } catch (Exception e) {
            LOGGER.info("msg=verifyPhoneErr", e);
            return Response.errInstance(e.getMessage());
        }
    }
}
