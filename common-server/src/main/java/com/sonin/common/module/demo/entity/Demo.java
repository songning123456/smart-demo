package com.sonin.common.module.demo.entity;

import com.sonin.common.tool.annotation.SqlAnno;
import lombok.Data;

/**
 * @author sonin
 * @date 2021/10/17 15:03
 */
@Data
public class Demo {

    @SqlAnno(primaryKey = "id", targetClass = DemoB.class, foreignKey = "aId")
    private DemoA demoA;

    @SqlAnno(primaryKey = "id", targetClass = DemoC.class, foreignKey = "bId")
    private DemoB demoB;

    @SqlAnno(primaryKey = "id", targetClass = DemoD.class, foreignKey = "cId")
    private DemoC demoC;

    @SqlAnno
    private DemoD demoD;

}
