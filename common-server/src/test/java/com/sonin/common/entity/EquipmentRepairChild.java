package com.sonin.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 设备维修-备品备件
 */
@Data
@TableName("equipment_repair_child")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EquipmentRepairChild {

    /**
     * id
     */
    private String id;
    /**
     * 维修ID
     */
    private String repairId;
    /**
     * 备品备件id
     */
    private String sparepartId;
    /**
     * 维修数量
     */
    private Integer repairNum;
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
