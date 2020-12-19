package com.iemij.test.config;

import com.iemij.test.filter.CommonFilter;
import com.iemij.test.interceptor.TestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = {"com.iemij.test.controller"})
public class SpringMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private InterceptProperties interceptProperties;

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
        registry.addInterceptor(new TestInterceptor())
                .addPathPatterns(interceptProperties.getSpecial()).excludePathPatterns(interceptProperties.getExclude());
    }

}
