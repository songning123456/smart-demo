package com.sonin.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author sonin
 * @date 2021/7/31 17:30
 */
@Slf4j
public class KafkaListenableFutureCallback implements ListenableFutureCallback<SendResult<String, String>> {

    private String topic;
    private String key;
    private String data;

    public KafkaListenableFutureCallback(String topic, String key, String data) {
        this.topic = topic;
        this.key = key;
        this.data = data;
    }

    @Override
    public void onFailure(Throwable throwable) {
        log.error("发送消息失败 => topic: {}, key: {}, data: {}, exception: {},", topic, key, data, throwable.getMessage());
    }

    @Override
    public void onSuccess(SendResult<String, String> sendResult) {
        log.error("发送消息成功 => topic: {}, key: {}, data: {}, exception: {},", topic, key, data, sendResult.toString());
    }
}
