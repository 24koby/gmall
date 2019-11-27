package com.hdu.gmall;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.hdu.gmall.manager.mapper")
@EnableDubbo
public class GmallManagerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmallManagerServiceApplication.class, args);
    }

}
