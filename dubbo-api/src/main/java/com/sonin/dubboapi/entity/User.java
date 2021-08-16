package com.sonin.dubboapi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sonin
 * @date 2021/8/7 8:48
 */
@Data
public class User implements Serializable {

    private String id;

    private String name;

    private Integer age;
}
