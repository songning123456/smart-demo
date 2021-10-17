package com.sonin.common.module.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.sonin.common.tool.annotation.SqlAnno;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * demo_b
 * </p>
 *
 * @author sonin
 * @since 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("demo_b")
public class DemoB extends Model {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * b_名称
     */
    private String bName;

    /**
     * a_id
     */
    private String aId;


}
