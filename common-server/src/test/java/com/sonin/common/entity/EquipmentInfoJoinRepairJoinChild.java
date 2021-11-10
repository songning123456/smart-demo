package com.sonin.common.entity;

import com.sonin.common.tool.annotation.JoinSqlAnno;
import lombok.Data;

/**
 * @author sonin
 * @date 2021/11/9 14:50
 */
@Data
public class EquipmentInfoJoinRepairJoinChild {

    @JoinSqlAnno(targetClass = EquipmentRepair.class, foreignKey = "infoId")
    private EquipmentInfo equipmentInfo;

    @JoinSqlAnno(targetClass = EquipmentRepair.class, foreignKey = "infoId")
    private EquipmentRepair equipmentRepair;

    @JoinSqlAnno
    private EquipmentRepairChild equipmentRepairChild;
}
