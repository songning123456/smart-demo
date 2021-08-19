package com.sonin.flowableserver.controller;

import com.sonin.flowableserver.entity.R;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/8/19 11:40
 */
@RestController
@RequestMapping("/flowable")
public class FlowableController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessEngine processEngine;

    /**
     * 提交采购订单的审批请求
     *
     * @param userId 用户id
     */
    @PostMapping("/start/{userId}/{purchaseOrderId}")
    public R startFlow(@PathVariable String userId, @PathVariable String purchaseOrderId) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        map.put("purchaseOrderId", purchaseOrderId);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("OrderApproval", map);
        String processId = processInstance.getId();
        String name = processInstance.getName();
        System.out.println(processId + ":" + name);
        return R.ok(processId + ":" + name);
    }

    /**
     * 获取用户的任务
     *
     * @param userId 用户id
     */
    @GetMapping("/getTasks/{userId}")
    public R getTasks(@PathVariable String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        return R.ok(tasks.toString());
    }

    /**
     * 审批通过
     */
    @PostMapping("/success/{taskId}")
    public R success(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return R.error("流程不存在");
        }
        // 通过审核
        Map<String, Object> map = new HashMap<>();
        map.put("approved", true);
        taskService.complete(taskId, map);
        return R.ok("流程审核通过！");
    }

    /**
     * 审批不通过
     */
    @PostMapping("/fail/{taskId}")
    public R fail(@PathVariable String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return R.error("流程不存在");
        }
        // 通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("approved", false);
        taskService.complete(taskId, map);
        return R.ok();
    }

    @RequestMapping(value = "processDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        // 流程走完的不显示图
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        // 使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(InstanceId).list();

        // 得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }
        // 获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        byte[] buf = new byte[1024];
        int length;
        try (InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, processEngineConfiguration.getActivityFontName(), processEngineConfiguration.getLabelFontName(), processEngineConfiguration.getAnnotationFontName(), processEngineConfiguration.getClassLoader(), 1.0); OutputStream out = httpServletResponse.getOutputStream()) {
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
        }
    }

}
