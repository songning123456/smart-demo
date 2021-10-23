package com.sonin.common.modules.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sonin.common.modules.common.entity.CommonSql;
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
public class CommonSqlServiceImpl extends ServiceImpl<CommonSqlMapper, CommonSql> implements ICommonSqlService {

    @Resource
    private CommonSqlMapper commonSqlMapper;

    public Map<String, Object> queryForMap(String sql) {
        return commonSqlMapper.queryForMap(sql);
    }

    @Override
    public Page<Map<String, Object>> queryForPage(Page page, String sql) {
        return commonSqlMapper.queryForPage(page, sql);
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql) {
        return commonSqlMapper.queryForList(sql);
    }

    @Override
    public Integer update(String sql) {
        return commonSqlMapper.update(sql);
    }

    @Override
    public Integer delete(String sql) {
        return commonSqlMapper.delete(sql);
    }

}
