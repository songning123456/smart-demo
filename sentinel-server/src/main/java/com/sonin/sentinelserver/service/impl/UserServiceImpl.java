package com.sonin.sentinelserver.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
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

    @Override
    @SentinelResource(value = "testSentinel", blockHandler = "getUserById2BlockHandler", fallback = "getUserById2Fallback")
    public User getUserById2(Integer id) {
        if (id != 1) {
            throw new RuntimeException("id error");
        }
        User user = new User();
        user.setId(id);
        user.setName("test");
        return user;
    }

    // 注意细节，一定要跟原函数的返回值和形参一致，并且形参最后要加个BlockException参数
    // 否则会报错，FlowException: null
    public User getUserById2BlockHandler(Integer id, BlockException e) {
        User user = new User();
        user.setId(id);
        user.setName("资源访问被限流");
        return user;
    }

    public User getUserById2Fallback(Integer id, Throwable throwable) {
        User user = new User();
        user.setId(id);
        user.setName(throwable.getMessage() + "熔断回调");
        return user;
    }

}
