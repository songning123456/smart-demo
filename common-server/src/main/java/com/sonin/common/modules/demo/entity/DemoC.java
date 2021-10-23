package com.sonin.common.modules.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sonin.common.tool.annotation.JoinSqlQueryAnno;
import lombok.Data;

/**
 * <p>
 * demo_c
 * </p>
 *
 * @author sonin
 * @since 2021-10-17
 */
@Data
@TableName("demo_c")
public class DemoC {

    private String id;

    /**
     * c_名称
     */
    @JoinSqlQueryAnno
    private String cName;

    /**
     * b_id
     */
    private String bId;

}
