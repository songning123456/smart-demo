package com.sonin.demo.controller;

import com.sonin.demo.service.QueryService;
import com.sonin.ssmframework.spring.annotation.Autowired;
import com.sonin.ssmframework.spring.annotation.Controller;
import com.sonin.ssmframework.spring.annotation.RequestMapping;
import com.sonin.ssmframework.spring.annotation.RequestParam;
import com.sonin.ssmframework.springmvc.entity.ModelAndView;

import java.util.Map;

/**
 * @author sonin
 * @date 2021/9/16 19:35
 */
@Controller
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private QueryService queryService;

    @RequestMapping("/query")
    public ModelAndView query(@RequestParam("name") String name) {
        Map<String, Object> result = queryService.queryByName(name);
        return new ModelAndView("first.html", result);
    }

}
