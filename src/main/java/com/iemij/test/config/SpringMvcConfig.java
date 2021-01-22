package com.iemij.test.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.iemij.test.common.DateDeserializer;
import com.iemij.test.common.DateSerializer;
import com.iemij.test.common.JsonMapper;
import com.iemij.test.filter.CommonFilter;
import com.iemij.test.interceptor.AdminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = {"com.iemij.test.controller"})
public class SpringMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private InterceptProperties interceptProperties;

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        final StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
//        stringConverter.setWriteAcceptCharset(false);//避免在response header中打印大量的 Accept-Charset
//
//        final ObjectMapper objectMapper = JsonMapper.getObjectMapperInstance();
//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(Date.class, new DateDeserializer());
//        module.addSerializer(Date.class, new DateSerializer());
//        objectMapper.registerModule(module);
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
//        final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//        jsonConverter.setObjectMapper(objectMapper);
//        converters.add(stringConverter);
//        converters.add(jsonConverter);
//        super.configureMessageConverters(converters);
//    }

    @Bean
    public FilterRegistrationBean commonFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CommonFilter commonFilter = new CommonFilter();
        registrationBean.setFilter(commonFilter);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns(interceptProperties.getSpecial()).excludePathPatterns(interceptProperties.getExclude());
    }
}
