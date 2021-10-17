package com.sonin.common.module.demo.controller;

import com.sonin.common.aop.annotation.CustomExceptionAnno;
import com.sonin.common.module.common.entity.Result;
import com.sonin.common.module.common.service.ISqlService;
import com.sonin.common.module.demo.dto.DemoDTO;
import com.sonin.common.module.demo.entity.Demo;
import com.sonin.common.module.demo.vo.DemoVO;
import com.sonin.common.tool.annotation.SqlAnno;
import com.sonin.common.tool.util.BeanExtUtils;
import com.sonin.common.tool.util.UniqIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private ISqlService sqlService;

    @CustomExceptionAnno(description = "多表关联-添加")
    @PostMapping(value = "/add")
    public Result<DemoVO> addCtrl(@RequestBody DemoDTO demoDTO) throws Exception {
        Result<DemoVO> result = new Result<>();
        Object object = BeanExtUtils.bean2Bean(demoDTO, Demo.class);
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<Class<?>, Object> class2ObjMap = new HashMap<>();
        for (int i = fields.length - 1; i >= 0; i--) {
            Field field = fields[i];
            SqlAnno sqlAnno = field.getAnnotation(SqlAnno.class);
            if (sqlAnno == null) {
                continue;
            }
            field.setAccessible(true);
            if (field.get(object) == null) {
                field.set(object, field.getType().newInstance());
            }
            // 设置唯一主键
            String uuid = UniqIdUtils.getInstance().getUniqID();
            Object srcObject = field.get(object);
            Field srcField = srcObject.getClass().getDeclaredField(sqlAnno.primaryKey());
            srcField.setAccessible(true);
            srcField.set(srcObject, uuid);
            srcField.setAccessible(false);
            Object targetObject = class2ObjMap.get(sqlAnno.targetClass());
            if (targetObject != null) {
                Field targetField = targetObject.getClass().getDeclaredField(sqlAnno.foreignKey());
                targetField.setAccessible(true);
                targetField.set(targetObject, uuid);
                targetField.setAccessible(false);
            }
            class2ObjMap.put(field.getType(), srcObject);
            field.setAccessible(false);
        }
        class2ObjMap.clear();
        sqlService.save(object);
        DemoVO demoVO = BeanExtUtils.bean2Bean(object, DemoVO.class);
        result.setResult(demoVO);
        return result;
    }

}

