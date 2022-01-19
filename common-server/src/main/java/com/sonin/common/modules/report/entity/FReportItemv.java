package com.sonin.common.modules.report.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 报表数据项值
 */
@Data
@TableName("f_report_itemv")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FReportItemv {

    /**
     * 主键id
     */
    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 报表数据项id
     */
    private String reitId;

    /**
     * 数据项值
     */
    private String itemValue;

    /**
     * 数据id
     */
    private String dataId;

    /**
     * 数据时间
     */
    private String dataTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 所属厂区
     */
    private String factoryId;

    /**
     * 创建人Id
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;

    /**
     * 修改人Id
     */
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;

    /**
     * 所属部门
     */
    private String createDept;

    /**
     * 所属公司
     */
    private String createCmpy;

    /**
     * 标识
     */
    private Integer delFlag;

}
