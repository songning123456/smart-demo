package com.sonin.dubboapi.service;

import com.sonin.dubboapi.entity.User;

/**
 * @author sonin
 * @date 2021/8/7 8:49
 */
public interface UserService {

    User getUserById(String id);
}
