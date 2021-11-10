package com.sonin.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 设备信息
 */
@Data
public class EquipmentInfo {

    /**
     * id
     */
    private String id;
    /**
     * 货主组织
     */
    private String departId;
    /**
     * 计量单位
     */
    private String measuringUnit;
    /**
     * 设备类型
     */
    private String equipmentType;
    /**
     * 设备编码
     */
    private String equipmentCode;
    /**
     * 设备名称
     */
    private String equipmentName;
    /**
     * 开始使用日期
     */
    private String startUseDate;
    /**
     * 限制使用年限
     */
    private Integer limitUseYear;
    /**
     * 建议更换时间
     */
    private String adviceReplaceDate;
    /**
     * 安装位置
     */
    private String installPosition;
    /**
     * 技术参数
     */
    private String technicalParameter;
    /**
     * 产品型号
     */
    private String productModel;
    /**
     * 厂家
     */
    private String manufacturer;
    /**
     * 材质
     */
    private String texture;
    /**
     * 设备图片
     */
    private String equipmentImg;
    /**
     * 设备二维码
     */
    private String qrCode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 资产id
     */
    private String assetId;
    /**
     * 创建人id
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    /**
     * 赤水 补充字段
     */
    private String deliveryMedium;

    private String installWay;

    private String equipmentBitNumber;

    private String structures;

    private String manufactureDate;

    private String manufactureNumber;

    private String intakeDate;

    private String originalValue;

    private String equipmentStatus;

    private Integer workingLife;

}
