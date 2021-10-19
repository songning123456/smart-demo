package com.sonin.common.module.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sonin.common.aop.annotation.CustomExceptionAnno;
import com.sonin.common.constant.Result;
import com.sonin.common.module.common.service.ICommonSqlService;
import com.sonin.common.module.common.service.ICrudSqlService;
import com.sonin.common.module.demo.dto.DemoDTO;
import com.sonin.common.module.demo.entity.Demo;
import com.sonin.common.module.demo.vo.DemoVO;
import com.sonin.common.tool.util.BeanExtUtils;
import com.sonin.common.tool.util.JoinSqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    private ICrudSqlService iCrudSqlService;
    @Autowired
    private ICommonSqlService iCommonSqlService;

    @CustomExceptionAnno(description = "多表关联-添加")
    @PostMapping(value = "/add")
    public Result<DemoVO> addCtrl(@RequestBody DemoDTO demoDTO) throws Exception {
        Result<DemoVO> result = new Result<>();
        Object object = BeanExtUtils.bean2Bean(demoDTO, Demo.class);
        JoinSqlUtils.setJoinSqlIdFunc(object);
        // iCrudSqlService.save(object);
        iCrudSqlService.save(((Demo) object).getDemoA(), ((Demo) object).getDemoB(), ((Demo) object).getDemoC(), ((Demo) object).getDemoD());
        DemoVO demoVO = BeanExtUtils.bean2Bean(object, DemoVO.class);
        result.setResult(demoVO);
        return result;
    }

    @CustomExceptionAnno(description = "多表关联-删除")
    @DeleteMapping(value = "/delete")
    public Result<?> deleteCtrl(@RequestBody DemoDTO demoDTO) throws Exception {
        Object object = BeanExtUtils.bean2Bean(demoDTO, Demo.class);
        JoinSqlUtils.checkSqlIdFunc(object);
        // iCrudSqlService.delete(object);
        iCrudSqlService.delete(((Demo) object).getDemoA(), ((Demo) object).getDemoB(), ((Demo) object).getDemoC(), ((Demo) object).getDemoD());
        return Result.ok("删除成功!");
    }

    @CustomExceptionAnno(description = "多表关联-编辑")
    @PutMapping("/edit")
    public Result<DemoVO> editCtrl(@RequestBody DemoDTO demoDTO) throws Exception {
        Result<DemoVO> result = new Result<>();
        Object object = BeanExtUtils.bean2Bean(demoDTO, Demo.class);
        JoinSqlUtils.checkSqlIdFunc(object);
        // iCrudSqlService.update(object);
        iCrudSqlService.update(((Demo) object).getDemoA(), ((Demo) object).getDemoB(), ((Demo) object).getDemoC(), ((Demo) object).getDemoD());
        DemoVO demoVO = BeanExtUtils.bean2Bean(object, DemoVO.class);
        result.setResult(demoVO);
        return result;
    }

    @CustomExceptionAnno(description = "多表关联-分页查询")
    @PostMapping("/page")
    public Result<Page<DemoVO>> pageCtrl(@RequestBody DemoDTO demoDTO) throws Exception {
        Result<Page<DemoVO>> result = new Result<>();
        Demo demo = BeanExtUtils.bean2Bean(demoDTO, Demo.class);
        // 不带拼接条件
        String sql = JoinSqlUtils.joinSqlQuery(demo);
        // 带拼接条件
        String sql2 = JoinSqlUtils.joinSqlTermQuery(demo);
        Page page = new Page(1, 10);
        Page<Map<String, Object>> pageMapList = iCommonSqlService.queryForPage(page, sql);
        List<DemoVO> demoVOList = JoinSqlUtils.maps2beans(pageMapList.getRecords(), DemoVO.class);
        page.setRecords(demoVOList);
        result.setResult(page);
        return result;
    }

}

