package com.sonin.common.modules.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * @author sonin
 * @date 2021/10/13 14:01
 */
public interface ICommonSqlService {

    Map<String, Object> queryForMap(String sql);

    Page<Map<String, Object>> queryForPage(Page<?> page, String sql);

    List<Map<String, Object>> queryForList(String sql);

    Integer update(String sql);

    Integer delete(String sql);

}
