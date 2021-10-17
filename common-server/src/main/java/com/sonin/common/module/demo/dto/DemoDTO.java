package com.sonin.common.module.demo.dto;

import com.sonin.common.module.demo.entity.DemoA;
import com.sonin.common.module.demo.entity.DemoB;
import com.sonin.common.module.demo.entity.DemoC;
import com.sonin.common.module.demo.entity.DemoD;
import com.sonin.common.tool.annotation.SqlAnno;
import lombok.Data;

/**
 * @author sonin
 * @date 2021/10/17 13:04
 */
@Data
public class DemoDTO {

    @SqlAnno(primaryKey = "id", targetClass = DemoB.class, foreignKey = "aId")
    private DemoA demoA;

    @SqlAnno(primaryKey = "id", targetClass = DemoC.class, foreignKey = "bId")
    private DemoB demoB;

    @SqlAnno(primaryKey = "id", targetClass = DemoD.class, foreignKey = "cId")
    private DemoC demoC;

    @SqlAnno
    private DemoD demoD;

    private Long pageNo = 1L;

    private Long pageSize = 10L;

}
