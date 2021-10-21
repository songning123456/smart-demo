package com.sonin.common.module.demo.controller;

import com.sonin.common.aop.annotation.CustomExceptionAnno;
import com.sonin.common.constant.Result;
import com.sonin.common.module.demo.dto.DemoADTO;
import com.sonin.common.module.demo.entity.DemoA;
import com.sonin.common.module.demo.vo.DemoAVO;
import com.sonin.common.tool.callback.IBeanConvertCallback;
import com.sonin.common.tool.util.BeanExtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/10/21 7:57
 */
@RestController
@RequestMapping("/demoA")
@Slf4j
public class DemoAController {

    @CustomExceptionAnno(description = "测试转换功能")
    @GetMapping("/bean2Bean")
    public Result<DemoAVO> convertCtrl(DemoADTO demoADTO) throws Exception {
        Result<DemoAVO> result = new Result<>();
        DemoA demoA = BeanExtUtils.bean2Bean(demoADTO, DemoA.class, new IBeanConvertCallback() {
            @Override
            public Object doBeanConvert(String targetFieldName, Object srcFieldVal) {
                return srcFieldVal;
            }
        });
        return result;
    }

    @CustomExceptionAnno(description = "测试转换功能")
    @GetMapping("/beans2Beans")
    public Result<DemoAVO> beans2BeansCtrl(DemoADTO demoADTO) throws Exception {
        Result<DemoAVO> result = new Result<>();
        List<DemoADTO> demoADTOList = new ArrayList<>();
        demoADTOList.add(demoADTO);
        DemoADTO demoADTO2 = new DemoADTO();
        demoADTO2.setId("34");
        demoADTO2.setAName("test2");
        demoADTOList.add(demoADTO2);
        List<DemoA> demoAList = BeanExtUtils.beans2Beans(demoADTOList, DemoA.class, new IBeanConvertCallback() {
            @Override
            public Object doBeanConvert(String targetFieldName, Object srcFieldVal) {
                return srcFieldVal;
            }
        });
        return result;
    }

    @CustomExceptionAnno(description = "测试转换功能")
    @GetMapping("/bean2Map")
    public Result<DemoAVO> bean2MapCtrl(DemoADTO demoADTO) throws Exception {
        Result<DemoAVO> result = new Result<>();
        Map<String, Object> map = BeanExtUtils.bean2Map(demoADTO);
        return result;
    }

    @CustomExceptionAnno(description = "测试转换功能")
    @GetMapping("/beans2Maps")
    public Result<DemoAVO> beans2MapsCtrl(DemoADTO demoADTO) throws Exception {
        Result<DemoAVO> result = new Result<>();
        List<DemoADTO> demoADTOList = new ArrayList<>();
        demoADTOList.add(demoADTO);
        DemoADTO demoADTO2 = new DemoADTO();
        demoADTO2.setId("34");
        demoADTO2.setAName("test2");
        demoADTOList.add(demoADTO2);
        List<Map<String, Object>> mapList = BeanExtUtils.beans2Maps(demoADTOList);
        return result;
    }

}
