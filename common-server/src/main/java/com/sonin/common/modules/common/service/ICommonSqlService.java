package com.sonin.common.modules.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/10/13 14:01
 */
public interface ICommonSqlService {

    Map<String, Object> queryForMap(String sql);

    Map<String, Object> queryWrapperForMap(String sql, QueryWrapper<?> queryWrapper);

    Page<Map<String, Object>> queryForPage(Page<?> page, String sql);

    Page<Map<String, Object>> queryWrapperForPage(Page<?> page, String sql, QueryWrapper<?> queryWrapper);

    List<Map<String, Object>> queryForList(String sql);

    List<Map<String, Object>> queryWrapperForList(String sql, QueryWrapper<?> queryWrapper);

    Integer update(String sql);

    Integer updateWrapper(String tableName, UpdateWrapper<?> updateWrapper);

    Integer delete(String sql);

    Integer deleteWrapper(String tableName, QueryWrapper<?> queryWrapper);

}
