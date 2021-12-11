package com.sonin.common.modules.consumer.entity;

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
@TableName("consumer_test")
public class ConsumerTest {

    private String id;

    private String name;

    private Integer kafkaPartition;

    private Long kafkaOffset;

    private Long kafkaTimestamp;

}
