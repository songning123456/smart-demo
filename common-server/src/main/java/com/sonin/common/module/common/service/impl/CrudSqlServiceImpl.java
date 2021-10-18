package com.sonin.common.module.common.service.impl;

import com.sonin.common.module.common.service.ICrudSqlService;
import com.sonin.common.tool.service.IJoinSqlCrudCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

/**
 * @author sonin
 * @date 2021/10/17 15:11
 */
@Service
@Slf4j
public class CrudSqlServiceImpl implements ICrudSqlService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean save(Object object) throws Exception {
        return this.doJoinSqlOption((sqlSession, sqlStatement, subObj) -> sqlSession.insert(sqlStatement + "insert", subObj), object);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean save(Object... subObjs) throws Exception {
        return this.doJoinSqlOption((sqlSession, sqlStatement, subObj) -> sqlSession.insert(sqlStatement + "insert", subObj), subObjs);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean update(Object object) throws Exception {
        return this.doJoinSqlOption((sqlSession, sqlStatement, subObj) -> {
            MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
            param.put("et", subObj);
            return sqlSession.update(sqlStatement + "updateById", param);
        }, object);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean update(Object... subObjs) throws Exception {
        return this.doJoinSqlOption((sqlSession, sqlStatement, subObj) -> {
            MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
            param.put("et", subObj);
            return sqlSession.update(sqlStatement + "updateById", param);
        }, subObjs);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean delete(Object... subObjs) throws Exception {
        return this.doJoinSqlOption((sqlSession, sqlStatement, subObj) -> sqlSession.delete(sqlStatement + "deleteById", subObj), subObjs);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean delete(Object object) throws Exception {
        return this.doJoinSqlOption((sqlSession, sqlStatement, subObj) -> sqlSession.delete(sqlStatement + "deleteById", subObj), object);
    }

    private Boolean doJoinSqlOption(IJoinSqlCrudCallback iJoinSqlCrudCallback, Object object) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Throwable var5 = null;
        try {
            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object subObj = field.get(object);
                field.setAccessible(false);
                if (subObj == null) {
                    continue;
                }
                this.doHandle(iJoinSqlCrudCallback, sqlSession, subObj);
            }
            sqlSession.flushStatements();
            return true;
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (sqlSession != null) {
                if (var5 != null) {
                    try {
                        sqlSession.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    sqlSession.close();
                }
            }
        }
    }

    private Boolean doJoinSqlOption(IJoinSqlCrudCallback iJoinSqlCrudCallback, Object... subObjs) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Throwable var5 = null;
        try {
            for (Object subObj : subObjs) {
                if (subObj == null) {
                    continue;
                }
                this.doHandle(iJoinSqlCrudCallback, sqlSession, subObj);
            }
            sqlSession.flushStatements();
            return true;
        } catch (Throwable var16) {
            var5 = var16;
            throw var16;
        } finally {
            if (sqlSession != null) {
                if (var5 != null) {
                    try {
                        sqlSession.close();
                    } catch (Throwable var15) {
                        var5.addSuppressed(var15);
                    }
                } else {
                    sqlSession.close();
                }
            }
        }
    }

    private void doHandle(IJoinSqlCrudCallback iJoinSqlCrudCallback, SqlSession sqlSession, Object subObj) throws Exception {
        String[] fieldNames = subObj.getClass().getName().split("\\.");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < fieldNames.length - 2; i++) {
            stringBuilder.append(".").append(fieldNames[i]);
        }
        stringBuilder.append(".mapper.").append(fieldNames[fieldNames.length - 1]).append("Mapper.");
        String sqlStatement = stringBuilder.toString().replaceFirst("\\.", "");
        Integer val0 = iJoinSqlCrudCallback.doJoinSqlCrud(sqlSession, sqlStatement, subObj);
        log.info("sql: {}crud, affectRows: {}", sqlStatement, val0);
    }

}
