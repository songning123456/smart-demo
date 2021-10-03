package com.sonin.common.module.user.controller;

import com.sonin.common.module.user.dto.UserDTO;
import com.sonin.common.module.user.entity.User;
import com.sonin.common.module.user.vo.UserVO;
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

    @PostMapping("/queryUser")
    public UserVO queryUserCtrl(@RequestBody UserDTO userDTO) throws Exception {
        // map => bean
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", "id1");
        map1.put("name", "name1");
        UserVO userVO1 = BeanUtils.map2Bean(map1, UserVO.class);
        // maps => beans
        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", "id2");
        map2.put("name", "name2");
        List<Map<String, Object>> mapList1 = new ArrayList<>();
        mapList1.add(map1);
        mapList1.add(map2);
        List<UserVO> userVOList1 = BeanUtils.maps2Beans(mapList1, UserVO.class);
        // bean => map
        Map<String, Object> map3 = BeanUtils.bean2Map(userDTO);
        // beans => maps
        List<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(userDTO);
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setId("id2");
        userDTO2.setName("name2");
        userDTOList.add(userDTO2);
        List<Map<String, Object>> mapList = BeanUtils.beans2Maps(userDTOList);
        return userVO1;
    }

}
