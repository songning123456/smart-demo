package com.sonin.demo.service.impl;

import com.sonin.demo.service.QueryService;
import com.sonin.ssmframework.spring.annotation.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/9/16 19:36
 */
@Service
public class QueryServiceImpl implements QueryService {

    @Override
    public Map<String, Object> queryByName(String name) {
        Map<String, Object> result = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());
        result.put("name", name);
        result.put("type", "query");
        result.put("time", time);
        return result;
    }

}
