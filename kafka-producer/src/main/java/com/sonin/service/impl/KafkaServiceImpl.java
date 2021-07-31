package com.sonin.service.impl;

import com.sonin.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author sonin
 * @date 2021/7/31 14:46
 */
@Service
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String topic, String key, String data) {

        ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topic, key, data);

        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info(topic + "生产者，发送消息失败：" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> stringObjectSendResult) {
                log.info(topic + "生产者，发送消息成功：" + stringObjectSendResult.toString());
            }

        });
    }
}
