package com.sonin.common.module.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.sonin.common.tool.annotation.JoinSqlAnno;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * demo_relation
 * </p>
 *
 * @author sonin
 * @since 2021-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("demo_relation")
public class DemoRelation extends Model {

    private static final long serialVersionUID = 1L;

    private String id;

    @JoinSqlAnno(primaryKey = "aId", targetClass = DemoA.class, foreignKey = "id")
    private String aId;

    @JoinSqlAnno(primaryKey = "bId", targetClass = DemoB.class, foreignKey = "id")
    private String bId;

    @JoinSqlAnno(primaryKey = "cId", targetClass = DemoC.class, foreignKey = "id")
    private String cId;

    @JoinSqlAnno(primaryKey = "dId", targetClass = DemoD.class, foreignKey = "id")
    private String dId;

}
