package com.sonin.common.modules.common.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/10/13 14:04
 */
public interface CommonSqlMapper {

    Map<String, Object> queryForMap(@Param("sql") String sql);

    Map<String, Object> queryWrapperForMap(@Param("sql") String sql, @Param(Constants.WRAPPER) QueryWrapper<?> queryWrapper);

    Page<Map<String, Object>> queryForPage(Page<?> page, @Param("sql") String sql);

    Page<Map<String, Object>> queryWrapperForPage(Page<?> page, @Param("sql") String sql, @Param(Constants.WRAPPER) QueryWrapper<?> queryWrapper);

    List<Map<String, Object>> queryForList(@Param("sql") String sql);

    List<Map<String, Object>> queryWrapperForList(@Param("sql") String sql, @Param(Constants.WRAPPER) QueryWrapper<?> queryWrapper);

    Integer update(@Param("sql") String sql);

    Integer updateWrapper(@Param("tableName") String tableName, @Param(Constants.WRAPPER) UpdateWrapper<?> updateWrapper);

    Integer delete(@Param("sql") String sql);

    Integer deleteWrapper(@Param("tableName") String tableName, @Param(Constants.WRAPPER) QueryWrapper<?> queryWrapper);

}
