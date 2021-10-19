package com.sonin.common.module.demo.entity;

import com.sonin.common.tool.annotation.JoinSqlAnno;
import lombok.Data;

/**
 * @author sonin
 * @date 2021/10/17 15:03
 * 必须排序
 */
@Data
public class Demo {

    @JoinSqlAnno(primaryKey = "id", targetClass = DemoB.class, foreignKey = "aId")
    private DemoA demoA;

    @JoinSqlAnno(primaryKey = "id", targetClass = DemoC.class, foreignKey = "bId")
    private DemoB demoB;

    @JoinSqlAnno(primaryKey = "id", targetClass = DemoD.class, foreignKey = "cId")
    private DemoC demoC;

    @JoinSqlAnno
    private DemoD demoD;





}
