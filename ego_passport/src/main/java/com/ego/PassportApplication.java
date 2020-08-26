package com.ego;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Auther: liuxw
 * @Date: 2019/8/16
 * @Description: com.ego
 * @version: 1.0
 */
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableDubbo
public class PassportApplication {
    public static void main(String[] args) {
        SpringApplication.run(PassportApplication.class, args);
    }
}
