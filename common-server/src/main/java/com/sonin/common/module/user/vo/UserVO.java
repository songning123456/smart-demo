package com.sonin.common.module.user.vo;

import com.sonin.common.module.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sonin
 * @date 2021/10/2 12:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends User {

    private String alias;

}