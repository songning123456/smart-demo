package com.sonin.kafkaapi;

import com.sonin.kafkaapi.producer.component.CustomKafkaProducer;
import com.sonin.kafkaapi.tool.CustomApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author sonin
 * @date 2021/12/11 8:54
 */
@SpringBootApplication
public class KafkaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaApiApplication.class, args);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            CustomKafkaProducer customKafkaProducer = CustomApplicationContext.getBean(CustomKafkaProducer.class);
            customKafkaProducer.close();
        }));
    }

}
