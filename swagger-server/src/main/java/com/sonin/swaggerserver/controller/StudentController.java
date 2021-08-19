package com.sonin.swaggerserver.controller;

import com.sonin.swaggerserver.entity.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sonin
 * @date 2021/8/19 16:39
 */
@RestController
@RequestMapping("/student")
@Api(tags = "学生信息")
public class StudentController {

    @GetMapping("/get")
    @ApiOperation(value = "学生信息-查询", notes = "学生信息-查询")
    public Student getStuCtrl() {
        Student student = new Student();
        student.setId(1);
        student.setName("swagger");
        student.setAge(15);
        student.setSex(1);
        return student;
    }
}
