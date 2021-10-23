package com.sonin.common.modules.demo.service.impl;

import com.sonin.common.modules.demo.entity.DemoA;
import com.sonin.common.modules.demo.mapper.DemoAMapper;
import com.sonin.common.modules.demo.service.IDemoAService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * demo_a 服务实现类
 * </p>
 *
 * @author sonin
 * @since 2021-10-17
 */
@Service
public class DemoAServiceImpl extends ServiceImpl<DemoAMapper, DemoA> implements IDemoAService {

}
