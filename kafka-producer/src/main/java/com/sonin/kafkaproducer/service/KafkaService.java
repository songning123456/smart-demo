package com.sonin.kafkaproducer.service;

/**
 * @author sonin
 * @date 2021/7/31 14:45
 */
public interface KafkaService {

    void send(String topic, String key, String data);
}
