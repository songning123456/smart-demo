package com.sonin.sentinelserver.service.impl;

import com.sonin.sentinelserver.entity.User;
import com.sonin.sentinelserver.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author sonin
 * @date 2021/8/31 19:36
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(Integer id) {
        User user = new User();
        user.setId(id);
        user.setName("test");
        return user;
    }
}
