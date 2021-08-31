package com.sonin.sentinelserver.entity;

import lombok.Data;

/**
 * @author sonin
 * @date 2021/8/31 19:35
 */
@Data
public class User {

    private Integer id;

    private String name;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
    }
}
