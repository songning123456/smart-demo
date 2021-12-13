package com.sonin.common.modules.kafka.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sonin.common.modules.kafka.entity.KafkaData;
import com.sonin.common.modules.kafka.service.IKafkaDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author sonin
 * @date 2021/12/11 11:01
 */
@Slf4j
@Component
public class CustomKafkaConsumer implements Runnable {

    private KafkaConsumer<String, String> kafkaConsumer;
    private Properties properties;
    private String topic;

    @Autowired
    private IKafkaDataService kafkaDataService;

    public CustomKafkaConsumer(@Value("${custom.kafka.bootstrap-servers}") String bootstrapServers,
                               @Value("${custom.kafka.consumer.group-id}") String groupId,
                               @Value("${custom.kafka.consumer.enable-auto-commit}") boolean enableAutoCommit,
                               @Value("${custom.kafka.consumer.session-timeout-ms}") Integer sessionTimeoutMs,
                               @Value("${custom.kafka.consumer.max-poll-records}") Integer maxPollRecords,
                               @Value("${custom.kafka.consumer.auto-offset-reset}") String autoOffsetReset,
                               @Value("${custom.kafka.consumer.key-serializer}") String keySerializer,
                               @Value("${custom.kafka.consumer.value-serializer}") String valueSerializer,
                               @Value("${custom.kafka.topic}") String topic) {
        String defaultKeySerializer = StringDeserializer.class.getName();
        String defaultValueSerializer = StringDeserializer.class.getName();
        try {
            defaultKeySerializer = Class.forName(keySerializer).getName();
            defaultValueSerializer = Class.forName(valueSerializer).getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        properties = new Properties();
        // kafka消费的的地址
        properties.put("bootstrap.servers", bootstrapServers);
        // 组名,不同组名可以重复消费
        properties.put("group.id", groupId);
        // 是否自动提交
        properties.put("enable.auto.commit", enableAutoCommit);
        // 超时时间
        properties.put("session.timeout.ms", sessionTimeoutMs);
        // 一次最大拉取的条数
        properties.put("max.poll.records", maxPollRecords);
        // earliest: 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
        // latest: 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
        // none: topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
        properties.put("auto.offset.reset", autoOffsetReset);
        // 序列化
        properties.put("key.deserializer", defaultKeySerializer);
        properties.put("value.deserializer", defaultValueSerializer);
        this.topic = topic;
    }

    private void subscribe(String... topic) {
        kafkaConsumer = new KafkaConsumer<>(properties);
        // 订阅主题列表topic
        kafkaConsumer.subscribe(Arrays.asList(topic));
    }

    /**
     * kafka的 (topic + partition + offset) 确定唯一索引
     * alter table tableName add unique key `kafka_unique_index` (`kafka_topic`,`kafka_partition`,`kafka_offset`)
     */
    private void consume() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(10000);
                log.info("线程: " + Thread.currentThread().getId() + " 开始消费kafka");
                if (consumerRecords != null && !consumerRecords.isEmpty()) {
                    for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                        log.info("当前消费数据: {}", consumerRecord.value());
                        KafkaData kafkaData = JSONObject.parseObject(consumerRecord.value(), KafkaData.class);
                        try {
                            kafkaData.setKafkaTopic(consumerRecord.topic());
                            kafkaData.setKafkaPartition(consumerRecord.partition());
                            kafkaData.setKafkaOffset(consumerRecord.offset());
                            kafkaData.setKafkaTimestamp(consumerRecord.timestamp());
                            kafkaDataService.save(kafkaData);
                        } catch (Exception e) {
                            if (e.getMessage().contains("Duplicate entry")) {
                                log.error("kafka重复消费数据: {}", JSON.toJSON(kafkaData));
                            } else {
                                e.printStackTrace();
                            }
                        }
                    }
                    // 关闭异步提交，使用同步提交
                    kafkaConsumer.commitSync();
                }
                TimeUnit.MINUTES.sleep(1);
            }
        } catch (Exception e) {
            log.error("kafka poll...中断, {}", e.getMessage());
        } finally {
            if (!Thread.currentThread().isInterrupted()) {
                close();
            }
        }
    }

    private void close() {
        log.info(">>> kafka消费被关闭 <<<");
        kafkaConsumer.close();
    }

    @Override
    public void run() {
        subscribe(this.topic.split(","));
        consume();
    }

}
