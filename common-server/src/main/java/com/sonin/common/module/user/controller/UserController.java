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

    private static Map<String, Object> nameAliasMap = new HashMap<>();
    private static Map<String, Object> idAliasMap = new HashMap<>();

    static {
        idAliasMap.put("1", "11");
        idAliasMap.put("2", "22");
        nameAliasMap.put("name1", "name11");
        nameAliasMap.put("name2", "name22");
    }

    @PostMapping("/queryUser")
    public UserVO queryUserCtrl(@RequestBody UserDTO userDTO) throws Exception {
        UserVO userVO = BeanExtUtils.bean2Bean(userDTO, UserVO.class, (targetFieldName, srcFieldVal) -> {
            if ("nameAlias".equals(targetFieldName)) {
                return nameAliasMap.get(srcFieldVal.toString());
            } else if ("idAlias".equals(targetFieldName)) {
                return idAliasMap.get(srcFieldVal.toString());
            } else {
                return "";
            }
        });
        return userVO;
    }

    @PostMapping("/queryUser2")
    public List<UserVO> queryUser2Ctrl(@RequestBody List<UserDTO> userDTOList) throws Exception {
        List<UserVO> userVOList = BeanExtUtils.beans2Beans(userDTOList, UserVO.class, (targetFieldName, srcFieldVal) -> {
            if ("nameAlias".equals(targetFieldName)) {
                return nameAliasMap.get(srcFieldVal.toString());
            } else if ("idAlias".equals(targetFieldName)) {
                return idAliasMap.get(srcFieldVal.toString());
            } else {
                return "";
            }
        });
        return userVOList;
    }

}
