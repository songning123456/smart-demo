package com.sonin.dubboprovider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sonin
 * @date 2021/8/7 8:55
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.sonin.dubboprovider")
public class DubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
    }
}
