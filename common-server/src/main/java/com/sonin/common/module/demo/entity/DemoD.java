package com.sonin.common.module.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * demo_d
 * </p>
 *
 * @author sonin
 * @since 2021-10-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("demo_d")
public class DemoD extends Model {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * d_名称
     */
    private String dName;

    /**
     * c_id
     */
    private String cId;


}
