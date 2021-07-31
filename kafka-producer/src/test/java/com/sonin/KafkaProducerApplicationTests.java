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
        kafkaService.send("topic-test", "key1", "value1");
    }

}
