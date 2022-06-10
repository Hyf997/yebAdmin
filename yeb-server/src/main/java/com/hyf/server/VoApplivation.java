package com.hyf.server;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @author：洪yf
 * @date：2022/3/4
 */

@SpringBootApplication
@MapperScan("com.hyf.server.mapper")
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class VoApplivation {
    public static void main(String[] args) {
        SpringApplication.run(VoApplivation.class,args);
    }
}
