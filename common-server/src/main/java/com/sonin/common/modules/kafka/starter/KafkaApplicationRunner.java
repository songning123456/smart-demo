package com.sonin.common.modules.kafka.starter;

import com.sonin.common.modules.kafka.component.CustomKafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author sonin
 * @date 2021/12/11 11:22
 */
@Slf4j
@Component
public class KafkaApplicationRunner implements ApplicationRunner {

    @Autowired
    private CustomKafkaConsumer customKafkaConsumer;

    @Override
    public void run(ApplicationArguments args) {
        Thread kafkaConsumerThread = new Thread(customKafkaConsumer);
        kafkaConsumerThread.start();
    }

}
