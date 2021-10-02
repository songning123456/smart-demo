package com.sonin.common.module.user.dto;

import com.sonin.common.module.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sonin
 * @date 2021/10/2 12:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends User {

    private String startTime;

    private String endTime;

}
