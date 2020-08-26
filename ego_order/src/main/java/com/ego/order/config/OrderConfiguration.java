package com.ego.order.config;

import com.ego.order.inteceptor.OrderInteceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: liuxw
 * @Date: 2019/8/20
 * @Description: com.ego.config
 * @version: 1.0
 */
@Configuration
public class OrderConfiguration implements WebMvcConfigurer {
    @Autowired
    private OrderInteceptor orderInteceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(orderInteceptor).addPathPatterns("/**");
    }
}
