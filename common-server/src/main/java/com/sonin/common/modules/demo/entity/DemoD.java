package com.sonin.common.modules.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * demo_d
 * </p>
 *
 * @author sonin
 * @since 2021-10-17
 */
@Data
@TableName("demo_d")
public class DemoD {

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
