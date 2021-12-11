package com.sonin.common.modules.consumer.service.impl;

import com.sonin.common.modules.consumer.entity.ConsumerTest;
import com.sonin.common.modules.consumer.mapper.ConsumerTestMapper;
import com.sonin.common.modules.consumer.service.IConsumerTestService;
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
public class ConsumerTestServiceImpl extends ServiceImpl<ConsumerTestMapper, ConsumerTest> implements IConsumerTestService {

}
