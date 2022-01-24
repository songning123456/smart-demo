package com.sonin.common.modules.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sonin.common.aop.annotation.CustomExceptionAnno;
import com.sonin.common.constant.Result;
import com.sonin.common.modules.common.mapper.CommonSqlMapper;
import com.sonin.common.modules.demo.dto.DemoADTO;
import com.sonin.common.modules.demo.entity.DemoA;
import com.sonin.common.modules.demo.service.IDemoAService;
import com.sonin.common.modules.demo.vo.DemoAVO;
import com.sonin.common.tool.callback.IBeanConvertCallback;
import com.sonin.common.tool.util.BeanExtUtils;
import com.sonin.common.tool.util.JoinSqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private IDemoAService iDemoAService;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private CommonSqlMapper commonSqlMapper;

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

    /**
     * https://blog.csdn.net/qq_26323323/article/details/81908955
     *
     * @return
     */
    @CustomExceptionAnno(description = "测试事务")
    @GetMapping("/testTran")
    public Result<?> testTranCtrl() {
        Result<?> result = new Result<>();
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(defaultTransactionDefinition);
        try {
            // do something
            DemoA demoA = new DemoA();
            demoA.setId("111");
            demoA.setAName("sss");
            iDemoAService.save(demoA);
            DefaultTransactionDefinition defaultTransactionDefinition2 = new DefaultTransactionDefinition();
            defaultTransactionDefinition2.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus transactionStatus2 = platformTransactionManager.getTransaction(defaultTransactionDefinition2);
            try {
                iDemoAService.remove(new QueryWrapper<>(demoA));
                // int a = 1/ 0;
                platformTransactionManager.commit(transactionStatus2);
            } catch (Exception e) {
                e.printStackTrace();
                platformTransactionManager.rollback(transactionStatus2);
            }
            platformTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            e.printStackTrace();
            platformTransactionManager.rollback(transactionStatus);
        }
        return result;
    }

    @GetMapping("/test")
    public void test() throws Exception {
        QueryWrapper<?> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("id", "232");
//        queryWrapper.getCustomSqlSegment();
//        JoinSqlUtils.checkSqlInject("demo_a");
//        int res = commonSqlMapper.deleteWrapper("demo_a", queryWrapper);
        UpdateWrapper<?> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("a_id", "123456");
        updateWrapper.set("b_id", "123456");
        updateWrapper.set("c_id", "123456");
        updateWrapper.set("d_id", "123456");
        updateWrapper.eq("id", 111);
        System.out.println(updateWrapper.getSqlSet());
        System.out.println(updateWrapper.getCustomSqlSegment());
        int res = commonSqlMapper.updateWrapper("demo_relation", updateWrapper);
        System.out.println(res);
    }

    @GetMapping("/outMemory")
    public Result<Object> outMemoryCtrl() {
        Result<Object> result = new Result<>();
        ArrayList<DemoA> list = new ArrayList<>();
        try {
            while (true) {
                list.add(new DemoA());
            }
        } catch (Error e) {
            e.printStackTrace();
        }
        result.setResult(list);
        return result;
    }

}
