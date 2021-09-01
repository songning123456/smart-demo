package com.sonin.sentinelserver;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.sonin.sentinelserver.util.SentinelUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sonin
 * @date 2021/8/31 19:41
 */
@SpringBootApplication
public class SentinelServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelServerApplication.class, args);

        //初始化限流规则
        SentinelUtil.initFlowQpsRule("getById", 20, RuleConstant.FLOW_GRADE_QPS, "default");
    }

}
