package com.sonin.common.modules.demo.dto;

import com.sonin.common.modules.demo.entity.DemoA;
import com.sonin.common.modules.demo.entity.DemoB;
import com.sonin.common.modules.demo.entity.DemoC;
import com.sonin.common.modules.demo.entity.DemoD;
import lombok.Data;

/**
 * @author sonin
 * @date 2021/10/17 13:04
 */
@Data
public class DemoDTO {

    private DemoA demoA;

    private DemoB demoB;

    private DemoC demoC;

    private DemoD demoD;

    private Long pageNo = 1L;

    private Long pageSize = 10L;

}