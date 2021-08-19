package com.sonin.flowableserver.delegate;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @author sonin
 * @date 2021/8/19 14:54
 */
@Slf4j
public class ReviewNoApprove implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        // 可以发送消息给某人
        log.info("拒绝，userId是：{}", delegateExecution.getVariable("userId"));
    }
}
