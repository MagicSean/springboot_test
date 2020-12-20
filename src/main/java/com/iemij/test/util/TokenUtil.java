package com.iemij.test.util;

import cn.hutool.core.convert.Convert;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;

public class TokenUtil {

    /**
     * 生成token
     * @param ttlMillis token有效期
     * @param claims 私密信息
     * @return token
     */
    public static String create(long ttlMillis, Map claims) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;//默认的加密方式
        long nowMillis = System.currentTimeMillis();//生成JWT的时间
        long expMillis = nowMillis + ttlMillis;//过期时间
        Date now = new Date(nowMillis);
        Date exp = new Date(expMillis);
        String id = Convert.toStr(nowMillis) + Convert.toStr(claims.get("uid"));
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)//自定义的私密内容
                .setId(id)//jwtId
                .setIssuedAt(now)//签发时间
                .setExpiration(exp)//过期时间
                .signWith(key, signatureAlgorithm);//签名方式
        return builder.compact();//生成token
    }

    /**
     * 根据秘钥生成jwt需要的key
     *
     * @return jwt需要的key
     */
    public static SecretKey generalKey() {
//        String str =  RandomUtil.randomString(64); //可以使用这个生成秘钥
        String stringKey = "apkyzes5vqma1ac8h8nqm9mcqr8fybqknmw56jxhj2hqu61zgbvc6ikufkwisv7n";//秘钥,需要64长度
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA256");
        return key;
    }

    /**
     * 根据token解析私密信息
     *
     * @param jwt token
     * @return 私密信息
     */
    public static Claims parse(String jwt) {
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

}
