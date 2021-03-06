package com.ego;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ego.mapper")
@EnableDubbo
public class DubboApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboApplication.class,args);
    }
}
