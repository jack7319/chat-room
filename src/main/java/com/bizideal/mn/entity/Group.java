package com.bizideal.mn.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : liulq
 * @date: 创建时间: 2018/1/19 11:19
 * @version: 1.0
 * @Description:
 */
public class Group {

    private Integer id;
    private String name;
    private List<UserInfo> userInfoList = new LinkedList<>();

    public Integer getId() {
        return id;
    }

    public Group setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Group setName(String name) {
        this.name = name;
        return this;
    }

    public List<UserInfo> getUserInfoList() {
        return userInfoList;
    }

    public Group setUserInfoList(List<UserInfo> userInfoList) {
        this.userInfoList = userInfoList;
        return this;
    }
}
