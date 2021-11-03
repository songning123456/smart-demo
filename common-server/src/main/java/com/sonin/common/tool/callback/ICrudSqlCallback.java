package com.sonin.common.tool.callback;

import org.apache.ibatis.session.SqlSession;
import org.springframework.lang.Nullable;

/**
 * @author sonin
 * @date 2021/10/17 19:45
 * 实现join sql的add、delete、edit
 */
@FunctionalInterface
public interface ICrudSqlCallback {

    @Nullable
    Integer doCrudSql(SqlSession sqlSession, String sqlStatement, Object object) throws Exception;

}