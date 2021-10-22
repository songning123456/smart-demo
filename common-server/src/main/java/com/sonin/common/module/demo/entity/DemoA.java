package com.sonin.common.module.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sonin.common.tool.annotation.JoinSqlQueryAnno;
import lombok.Data;

/**
 * <p>
 * demo_a
 * </p>
 *
 * @author sonin
 * @since 2021-10-17
 */
@Data
@TableName("demo_a")
public class DemoA {

    @JoinSqlQueryAnno
    private String id;

    /**
     * a_名称
     */
    private String aName;

}
