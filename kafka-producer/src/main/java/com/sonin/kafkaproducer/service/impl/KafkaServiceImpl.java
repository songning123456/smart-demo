package com.sonin.kafkaproducer.service.impl;

import com.sonin.kafkaproducer.entity.KafkaListenableFutureCallback;
import com.sonin.kafkaproducer.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

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

        listenableFuture.addCallback(new KafkaListenableFutureCallback(topic, key, data));
    }
}
