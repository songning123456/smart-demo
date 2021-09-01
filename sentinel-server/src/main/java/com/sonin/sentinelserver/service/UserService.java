package com.sonin.sentinelserver.service;

import com.sonin.sentinelserver.entity.User;

/**
 * @author sonin
 * @date 2021/8/31 19:35
 */
public interface UserService {

    User getUserById(Integer id);

    User getUserById2(Integer id);
}
