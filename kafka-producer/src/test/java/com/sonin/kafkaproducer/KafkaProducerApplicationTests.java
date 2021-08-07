package com.sonin.kafkaproducer;

import com.sonin.kafkaproducer.service.KafkaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaProducerApplicationTests {

    @Autowired
    private KafkaService kafkaService;

    @Test
    void contextLoads() {
        for (int i = 0; i < 100; i++) {
            kafkaService.send("topic-test", "key" + i, "value" + i);
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
