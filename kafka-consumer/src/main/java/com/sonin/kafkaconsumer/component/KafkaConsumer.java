package com.sonin.kafkaconsumer.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.stereotype.Component;

/**
 * @author sonin
 * @date 2021/7/31 15:31
 */
@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = {"topic-test"}, errorHandler = "consumerAwareListenerErrorHandler")
    public void consume(ConsumerRecord<String, String> consumerRecord) {
        String key = consumerRecord.key();
        String value = consumerRecord.value();
        log.info("key: {}, value: {}", key, value);
    }

    @Bean
    public ConsumerAwareListenerErrorHandler consumerAwareListenerErrorHandler() {

        return (message, exception, consumer) -> {
            log.error("kafka consumer error! message: {}, exception: {}", message, exception);
            return null;
        };
    }

}
