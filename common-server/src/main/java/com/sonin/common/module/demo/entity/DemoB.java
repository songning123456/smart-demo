package com.sonin.common.module.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sonin.common.tool.annotation.JoinSqlQueryAnno;
import com.sonin.common.tool.enums.JoinSqlQueryEnum;
import lombok.Data;

/**
 * <p>
 * demo_b
 * </p>
 *
 * @author sonin
 * @since 2021-10-17
 */
@Data
@TableName("demo_b")
public class DemoB {

    @JoinSqlQueryAnno(joinSqlQueryEnum = JoinSqlQueryEnum.LIKE)
    private String id;

    /**
     * b_名称
     */
    @JoinSqlQueryAnno(isUsed = false)
    private String bName;

    /**
     * a_id
     */
    private String aId;

}
