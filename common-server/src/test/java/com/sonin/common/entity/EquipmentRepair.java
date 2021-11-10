package com.sonin.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 设备维修流程
 */
@Data
@TableName("equipment_repair")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EquipmentRepair {

    /**
     * id
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 设备id
     */
    private String infoId;

    /**
     * 维修单号
     */
    private String repairOrder;
    /**
     * 故障时间
     */
    private String faultDate;
    /**
     * 报修人
     */
    private String repairUser;
    /**
     * 报修人电话
     */
    private String repairUserPhone;
    /**
     * 报修时间
     */
    private String repairDate;
    /**
     * 报修照片
     */
    private String faultPhoto;
    /**
     * 维修用时
     */
    private Integer costHour;
    /**
     * 故障原因
     */
    private String faultReason;
    /**
     * 工作描述
     */
    private String jobDescription;
    /**
     * 办结标记，yes办结
     */
    private String finishFlag;
    /**
     * 流程定义id
     */
    private String processDefinitionId;
    /**
     * 流程实例id
     */
    private String processInstanceId;
    /**
     * 创建人id
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人id
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 所属部门
     */
    private String createDept;
    /**
     * 所属公司
     */
    private String createCmpy;
    /**
     * 删除标识，0:正常,1:删除
     */
    private Integer delFlag;
}
