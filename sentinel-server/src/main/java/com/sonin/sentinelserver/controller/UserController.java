package com.sonin.sentinelserver.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sonin.sentinelserver.entity.User;
import com.sonin.sentinelserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author sonin
 * @date 2021/8/31 19:39
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    private static AtomicLong all = new AtomicLong(0);
    private static AtomicLong success = new AtomicLong(0);
    private static AtomicLong error = new AtomicLong(0);

    @GetMapping("/getByCatch")
    public User getByCatchCtrl() {
        int id = 1;
        log.info("all总共调用了{}次", all.incrementAndGet());
        User user = null;
        Entry entry = null;
        try {
            // 被保护的业务逻辑
            entry = SphU.entry("getById");
            log.info("success总共调用了{}次", success.incrementAndGet());
            user = userService.getUserById(id);
        } catch (BlockException e) {
            log.info("error总共调用了{}次", error.incrementAndGet());
            // 资源访问阻止，被限流或被降级
            return new User(id, "资源访问被限流");
        } catch (Exception e) {
            // 若需要配置降级规则，需要通过这种方式记录业务异常
            Tracer.traceEntry(e, entry);
        } finally {
            // 务必保证exit，务必保证每个entry与exit配对
            if (entry != null) {
                entry.exit();
            }
        }
        return user;
    }

}
