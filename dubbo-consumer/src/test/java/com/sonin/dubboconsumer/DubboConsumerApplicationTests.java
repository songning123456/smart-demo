package com.sonin.dubboconsumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.sonin.dubboapi.entity.User;
import com.sonin.dubboapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableDubbo
class DubboConsumerApplicationTests {

    @Reference
    UserService userService;

    @Test
    void contextLoads() {
        User user = userService.getUserById("01");
        System.out.println(user);
    }

}
