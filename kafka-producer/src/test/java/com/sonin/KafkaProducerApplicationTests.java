package com.sonin;

import com.sonin.service.KafkaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KafkaProducerApplicationTests {

    @Autowired
    private KafkaService kafkaService;

    @Test
    void contextLoads() {
        for (int i = 0; i < 10000; i++) {
            long currentTime = System.currentTimeMillis();
            kafkaService.send("topic-test", "key" + currentTime, "value" + currentTime);
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
