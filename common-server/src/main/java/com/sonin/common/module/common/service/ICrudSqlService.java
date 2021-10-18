package com.sonin.common.module.common.service;

/**
 * @author sonin
 * @date 2021/10/17 15:10
 * 方法重载时,存在可变参数方法和定长参数方法,优先调用定长参数方法！
 */
public interface ICrudSqlService {

    Boolean save(Object object) throws Exception;

    Boolean save(Object... subObjs) throws Exception;

    Boolean update(Object object) throws Exception;

    Boolean update(Object... subObjs) throws Exception;

    Boolean delete(Object object) throws Exception;

    Boolean delete(Object... subObjs) throws Exception;
}
