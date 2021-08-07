package com.sonin.dubboprovider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sonin.dubboapi.entity.User;
import com.sonin.dubboapi.service.UserService;
import org.springframework.stereotype.Component;

/**
 * @author sonin
 * @date 2021/8/7 11:24
 */
@Component
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUserById(String id) {
        User user = new User();
        user.setId("00000001");
        user.setName("sonin");
        user.setAge(27);
        return user;
    }

}
