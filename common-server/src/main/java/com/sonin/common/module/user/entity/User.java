package com.sonin.common.module.user.entity;

import com.sonin.common.tool.annotation.BeanAnno;
import lombok.Data;

/**
 * @author sonin
 * @date 2021/10/2 12:21
 */
@Data
public class User {

    @BeanAnno(targetFieldName = "idAlias")
    private String id;

    @BeanAnno(targetFieldName = "nameAlias")
    private String name;

}
