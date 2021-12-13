package com.sonin.common.modules.kafka.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * consumer_test
 * </p>
 *
 * @author sonin
 * @since 2021-12-11
 */
@Data
@TableName("kafka_data")
public class KafkaData {

    private String id;

    private String name;

    private String kafkaTopic;

    private Integer kafkaPartition;

    private Long kafkaOffset;

    private Long kafkaTimestamp;

}
