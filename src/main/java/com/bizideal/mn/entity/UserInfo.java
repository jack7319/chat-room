package com.bizideal.mn.entity;

import java.io.Serializable;

/**
 * @author : liulq
 * @date: 创建时间: 2018/1/19 11:17
 * @version: 1.0
 * @Description:
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -2027130822212645743L;
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public UserInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserInfo setName(String name) {
        this.name = name;
        return this;
    }
}
