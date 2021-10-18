package com.sonin.common.module.demo.controller;

import com.sonin.common.aop.annotation.CustomExceptionAnno;
import com.sonin.common.module.common.entity.Result;
import com.sonin.common.module.common.service.ISqlService;
import com.sonin.common.module.demo.dto.DemoDTO;
import com.sonin.common.module.demo.entity.Demo;
import com.sonin.common.module.demo.vo.DemoVO;
import com.sonin.common.tool.util.BeanExtUtils;
import com.sonin.common.tool.util.JoinSqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        JoinSqlUtils.setJoinSqlIdFunc(object);
        sqlService.save(object);
        DemoVO demoVO = BeanExtUtils.bean2Bean(object, DemoVO.class);
        result.setResult(demoVO);
        return result;
    }

    @CustomExceptionAnno(description = "多表关联-删除")
    @DeleteMapping(value = "/delete")
    public Result<?> deleteCtrl(@RequestBody DemoDTO demoDTO) throws Exception {
        Object object = BeanExtUtils.bean2Bean(demoDTO, Demo.class);
        JoinSqlUtils.checkSqlIdFunc(object);
        sqlService.delete(object);
        return Result.ok("删除成功!");
    }

    @CustomExceptionAnno(description = "多表关联-编辑")
    @PutMapping("/edit")
    public Result<DemoVO> editCtrl(@RequestBody DemoDTO demoDTO) throws Exception {
        Result<DemoVO> result = new Result<>();
        Object object = BeanExtUtils.bean2Bean(demoDTO, Demo.class);
        JoinSqlUtils.checkSqlIdFunc(object);
        sqlService.update(object);
        DemoVO demoVO = BeanExtUtils.bean2Bean(object, DemoVO.class);
        result.setResult(demoVO);
        return result;
    }

}
