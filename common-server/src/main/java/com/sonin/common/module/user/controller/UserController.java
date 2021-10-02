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
        // demo1
        User user = BeanUtils.bean2Bean(userDTO, User.class);
        UserVO userVO = BeanUtils.bean2Bean(user, UserVO.class);
        return userVO;
    }

}
