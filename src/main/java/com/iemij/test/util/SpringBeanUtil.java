package com.iemij.test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanUtil implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(SpringBeanUtil.class);

    private static ApplicationContext ctx;

    /**
     * 通过bean的id取得bean对象
     * @param id spring bean ID值
     * @return spring bean对象
     */
    public static Object getBean(String id) {
        if (ctx == null) {
            throw new NullPointerException("ApplicationContext is null");
        }
        return ctx.getBean(id);
    }

    /**
     * 通过bean的class取得bean对象
     * @param beanClass spring bean的Class
     * @return spring bean对象
     */
    public static Object getBean(Class beanClass) {
        if (ctx == null) {
            throw new NullPointerException("ApplicationContext is null");
        }
        return ctx.getBean(beanClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationcontext)
            throws BeansException {
        ctx = applicationcontext;
    }
}
