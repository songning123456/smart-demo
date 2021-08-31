package com.sonin.sentinelserver.util;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.sonin.sentinelserver.controller.UserController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sonin
 * @date 2021/8/31 19:46
 */
public class SentinelUtil {

    public static void initFlowQpsRule(String resourceName, double count, int grade, String limitApp) {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule(resourceName);
        // set limit qps to 2
        rule.setCount(count);
        rule.setGrade(grade);
        rule.setLimitApp(limitApp);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
