package com.sonin.common.modules.kafka.service.impl;

import com.sonin.common.modules.kafka.entity.KafkaData;
import com.sonin.common.modules.kafka.mapper.KafkaDataMapper;
import com.sonin.common.modules.kafka.service.IKafkaDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * consumer_test 服务实现类
 * </p>
 *
 * @author sonin
 * @since 2021-12-11
 */
@Service
public class KafkaDataServiceImpl extends ServiceImpl<KafkaDataMapper, KafkaData> implements IKafkaDataService {

}
