package com.iemij.test.common;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

public class DateDeserializer extends JsonDeserializer<Date> {

    private static final Logger logger = LoggerFactory.getLogger(DateDeserializer.class);

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String text = jsonParser.getText();
        if (StrUtil.isEmpty(text)) {
            return null;
        }
        try {
            return DateUtil.parse(text, DatePattern.NORM_DATETIME_PATTERN);
        } catch (Exception e) {
            logger.error("msg=parseDateErr text={}", text, e);
            return null;
        }
    }
}
