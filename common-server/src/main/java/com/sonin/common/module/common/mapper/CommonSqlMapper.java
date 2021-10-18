package com.sonin.common.module.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sonin.common.module.common.entity.CommonSql;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/10/13 14:04
 */
public interface CommonSqlMapper extends BaseMapper<CommonSql> {

    Map<String, Object> queryForMap(@Param("sql") String sql);

    Page<Map<String, Object>> queryForPage(Page page, @Param("sql") String sql);

    List<Map<String, Object>> queryForList(@Param("sql") String sql);

    Integer update(@Param("sql") String sql);

    Integer delete(@Param("sql") String sql);

}
