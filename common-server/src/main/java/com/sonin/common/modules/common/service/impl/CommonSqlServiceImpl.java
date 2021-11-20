package com.sonin.common.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sonin.common.modules.common.mapper.CommonSqlMapper;
import com.sonin.common.modules.common.service.ICommonSqlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/10/13 14:03
 */
@Service
public class CommonSqlServiceImpl implements ICommonSqlService {

    @Resource
    private CommonSqlMapper commonSqlMapper;

    @Override
    public Map<String, Object> queryForMap(String sql) {
        return commonSqlMapper.queryForMap(sql);
    }

    @Override
    public Map<String, Object> queryWrapperForMap(String sql, QueryWrapper<?> queryWrapper) {
        return commonSqlMapper.queryWrapperForMap(sql, queryWrapper);
    }

    @Override
    public Page<Map<String, Object>> queryForPage(Page<?> page, String sql) {
        return commonSqlMapper.queryForPage(page, sql);
    }

    @Override
    public Page<Map<String, Object>> queryWrapperForPage(Page<?> page, String sql, QueryWrapper<?> queryWrapper) {
        return commonSqlMapper.queryWrapperForPage(page, sql, queryWrapper);
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql) {
        return commonSqlMapper.queryForList(sql);
    }

    @Override
    public List<Map<String, Object>> queryWrapperForList(String sql, QueryWrapper<?> queryWrapper) {
        return commonSqlMapper.queryWrapperForList(sql, queryWrapper);
    }

    @Override
    public Integer update(String sql) {
        return commonSqlMapper.update(sql);
    }

    @Override
    public Integer updateWrapper(String tableName, UpdateWrapper<?> updateWrapper) {
        return commonSqlMapper.updateWrapper(tableName, updateWrapper);
    }

    @Override
    public Integer delete(String sql) {
        return commonSqlMapper.delete(sql);
    }

    @Override
    public Integer deleteWrapper(String tableName, QueryWrapper<?> queryWrapper) {
        return commonSqlMapper.deleteWrapper(tableName, queryWrapper);
    }

}
