package com.sonin.common.modules.common.mapper;

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

    Page<Map<String, Object>> queryForPage(Page page, @Param("sql") String sql);

    List<Map<String, Object>> queryForList(@Param("sql") String sql);

    Integer update(@Param("sql") String sql);

    Integer delete(@Param("sql") String sql);

}
