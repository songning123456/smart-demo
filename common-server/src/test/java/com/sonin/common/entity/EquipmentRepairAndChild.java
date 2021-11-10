package com.sonin.common.entity;

import com.sonin.common.tool.annotation.JoinSqlAnno;
import lombok.Data;

/**
 * @author sonin
 * @date 2021/11/9 10:17
 */
@Data
public class EquipmentRepairAndChild {

    @JoinSqlAnno(primaryKey = "id", targetClass = EquipmentRepair.class, foreignKey = "infoId")
    private EquipmentInfo equipmentInfo;

    @JoinSqlAnno
    private EquipmentRepair equipmentRepair;


    public EquipmentRepairAndChild() {
        this.equipmentInfo = new EquipmentInfo();
        this.equipmentRepair = new EquipmentRepair();
    }
}
