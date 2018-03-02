package com.bizideal.mn.config;

import com.bizideal.mn.filter.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author : liulq
 * @date: 创建时间: 2018/1/19 15:14
 * @version: 1.0
 * @Description:
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(new LoginInterceptor());
        ir.excludePathPatterns("/toPage/login");
        ir.excludePathPatterns("/");
        ir.excludePathPatterns("/login");
        super.addInterceptors(registry);
    }
}
