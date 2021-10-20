package com.sonin.common.module.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.sonin.common.tool.annotation.JoinSqlQueryAnno;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * demo_c
 * </p>
 *
 * @author sonin
 * @since 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("demo_c")
public class DemoC extends Model {

    private static final long serialVersionUID = 1L;

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
