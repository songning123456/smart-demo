package com.sonin.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sonin
 * @date 2021/10/2 10:56
 */
@Slf4j
@SpringBootApplication
public class CommonServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonServerApplication.class, args);
    }

}
