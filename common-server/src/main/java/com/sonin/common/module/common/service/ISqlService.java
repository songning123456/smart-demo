package com.sonin.common.module.common.service;

/**
 * @author sonin
 * @date 2021/10/17 15:10
 */
public interface ISqlService {

    Boolean save(Object object) throws IllegalAccessException;

    Boolean update(Object object) throws IllegalAccessException;

    Boolean delete(Object object) throws IllegalAccessException;
}
