package com.sonin.kafkaapi.producer.component;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author sonin
 * @date 2021/12/11 9:12
 */
@Component
public class CustomKafkaProducer {

    private KafkaProducer<String, String> kafkaProducer;
    private Properties properties;

    public CustomKafkaProducer(@Value("${custom.kafka.bootstrap-servers}") String bootstrapServers,
                               @Value("${custom.kafka.producer.retries}") Integer retries,
                               @Value("${custom.kafka.producer.batch-size}") Integer batchSize,
                               @Value("${custom.kafka.producer.acks}") String acks,
                               @Value("${custom.kafka.producer.key-serializer}") String keySerializer,
                               @Value("${custom.kafka.producer.value-serializer}") String valueSerializer) {
        String defaultKeySerializer = StringSerializer.class.getName();
        String defaultValueSerializer = StringSerializer.class.getName();
        try {
            defaultKeySerializer = Class.forName(keySerializer).getName();
            defaultValueSerializer = Class.forName(valueSerializer).getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("retries", retries);
        properties.put("batch.size", batchSize);
        properties.put("acks", acks);
        properties.put("key.serializer", defaultKeySerializer);
        properties.put("value.serializer", defaultValueSerializer);
    }

    public void send(String topic, String key, String message) {
        try {
            kafkaProducer.send(new ProducerRecord<>(topic, key, message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open() {
        this.kafkaProducer = new KafkaProducer<>(properties);
    }

    public void close() {
        kafkaProducer.close();
    }

}
