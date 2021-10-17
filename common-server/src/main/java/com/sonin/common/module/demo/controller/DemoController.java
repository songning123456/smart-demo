package com.sonin.common.module.demo.controller;


import com.sonin.common.aop.annotation.CustomExceptionAnno;
import com.sonin.common.module.common.entity.Result;
import com.sonin.common.module.demo.dto.DemoDTO;
import com.sonin.common.module.demo.vo.DemoVO;
import com.sonin.common.tool.annotation.SqlAnno;
import com.sonin.common.tool.util.BeanExtUtils;
import com.sonin.common.tool.util.UniqIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;

/**
 * <p>
 * demo 前端控制器
 * </p>
 *
 * @author sonin
 * @since 2021-10-17
 * 多表关联增删改查(测试A、B、C、D四张表)
 */
@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {

    @CustomExceptionAnno(description = "多表关联-添加")
    @PostMapping(value = "/add")
    public Result<DemoVO> addCtrl(@RequestBody DemoDTO demoDTO) throws Exception {
        Result<DemoVO> result = new Result<>();
        Object dtoObject = demoDTO;
        Class<?> dtoClass = dtoObject.getClass();
        Field[] dtoFields = dtoClass.getDeclaredFields();
        for (Field dtoField : dtoFields) {
            dtoField.setAccessible(true);
            if (dtoField.getAnnotation(SqlAnno.class) != null && dtoField.get(dtoObject) == null) {
                dtoField.set(dtoObject, dtoField.getType().newInstance());
            }
        }
        for (Field dtoField : dtoFields) {
            SqlAnno sqlAnno = dtoField.getAnnotation(SqlAnno.class);
            if (sqlAnno != null) {
                // 设置唯一主键
                String uuid = UniqIdUtils.getInstance().getUniqID();
                Object srcObject = dtoField.get(dtoObject);
                Field srcField = srcObject.getClass().getDeclaredField(sqlAnno.primaryKey());
                srcField.setAccessible(true);
                srcField.set(srcObject, uuid);
                srcField.setAccessible(false);
                for (Field dtoField2 : dtoFields) {
                    if (dtoField2.getType() == sqlAnno.targetClass()) {
                        Object targetObject = dtoField2.get(dtoObject);
                        Field targetField = targetObject.getClass().getDeclaredField(sqlAnno.foreignKey());
                        targetField.setAccessible(true);
                        targetField.set(targetObject, uuid);
                        targetField.setAccessible(false);
                    }
                }
            }
        }
        DemoVO demoVO = BeanExtUtils.bean2Bean(dtoObject, DemoVO.class);
        result.setResult(demoVO);
        return result;
    }
}

