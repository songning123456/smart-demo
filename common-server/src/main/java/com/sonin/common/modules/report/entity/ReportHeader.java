package com.sonin.common.modules.report.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 报表表头
 */
@Data
@TableName("report_header")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ReportHeader {

    /**
     * 表头ID
     */
    @TableId(type = IdType.UUID)
    private String id;

    /**
     * 报表ID
     */
    private String reportId;

    /**
     * 是否为第一行表头
     */
    private String isFirst;

    /**
     * 父id
     */
    private String fid;

    /**
     * 标题
     */
    private String title;

    /**
     * 是否编辑
     */
    private String isCanEdit;

    /**
     * 合并行
     */
    private String rowSpan;

    /**
     * 合并列
     */
    private String colSpan;

    /**
     * 1、实时2、填报
     */
    private String keyType;

    /**
     * 指标字段
     */
    private String keyIndex;

    /**
     * 排序号
     */
    private Integer sortNum;

    /**
     * 子集
     */
    @TableField(exist = false)
    private List<ReportHeader> children;

    /**
     * 创建人员
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改人员
     */
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 修改部门
     */
    private String createDept;

    /**
     * 修改集团
     */
    private String createCmpy;

    /**
     * 标识
     */
    private Integer delFlag;
    @TableField(exist = false)
    private String key;
}
