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
 * 报表数据项管理
 */
@Data
@TableName("f_report_item")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FReportItem implements Comparable<FReportItem> {

    /**
     * 主键id
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 报表id
     */
    private String reportId;
    /**
     * 数据项id
     */
    private String itemId;
    /**
     * 数据项别名
     */
    private String itemAlias;
    /**
     * 数据项编码
     */
    private String itemCode;
    /**
     * 数据项单位
     */
    private String unit;
    /**
     * 数据有效性最大
     */
    private Double trendMax;
    /**
     * 数据有效性最小
     */
    private Double trendMin;
    /**
     * 报警上限
     */
    private Double alarmMax;
    /**
     * 超出上限背景色
     */
    private String alarmMaxColor;
    /**
     * 报警下限
     */
    private Double alarmMin;
    /**
     * 低于下限背景色
     */
    private String alarmMinColor;
    /**
     * 文本框类型
     */
    private String textType;
    /**
     * 字典CODE/时间格式/正则
     */
    private String typeInit;
    /**
     * 1是0否
     */
    private Integer required;
    /**
     * 备注
     */
    private String remark;
    /**
     * 排序序号
     */
    private Integer sortNum;
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
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
    /**
     * 修改人Id
     */
    private String updateBy;
    /**
     * 修改时间
     */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
     * 删除标识
     */
    private Integer delFlag;

    /**
     * 是否数据分析能效分析指标
     */
    private String isUsePowerAnalyse;
    /**
     * 是否必填项
     */
    private String isMust;
    /**
     * 机构编码
     */
    private String orgCode;

    @Override
    public int compareTo(FReportItem fReportItem) {
        return this.sortNum - fReportItem.getSortNum();
    }
}
