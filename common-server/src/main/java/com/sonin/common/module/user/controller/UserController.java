package com.sonin.common.module.user.controller;

import com.sonin.common.module.user.dto.UserDTO;
import com.sonin.common.module.user.entity.User;
import com.sonin.common.module.user.vo.UserVO;
import com.sonin.common.tool.service.IBeanConvertCallback;
import com.sonin.common.tool.util.BeanExtUtils;
import com.sonin.common.tool.util.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/10/2 12:23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static Map<String, Object> aliasMap = new HashMap<>();
    private static Map<String, Object> idMap = new HashMap<>();

    static {
        aliasMap.put("123", "456");
        idMap.put("1", "111");
    }

    @PostMapping("/queryUser")
    public UserVO queryUserCtrl(@RequestBody UserDTO userDTO) throws Exception {
        UserVO userVO = BeanExtUtils.bean2Bean(userDTO, UserVO.class, (targetFieldName, srcFieldVal) -> {
            if ("alias".equals(targetFieldName)) {
                return aliasMap.get(srcFieldVal.toString());
            } else if ("id2".equals(targetFieldName)) {
                return idMap.get(srcFieldVal.toString());
            } else {
                return "";
            }
        });
        return userVO;
    }

}
