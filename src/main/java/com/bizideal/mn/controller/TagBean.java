package com.bizideal.mn.controller;

public class TagBean implements Comparable<TagBean> {

    // 标签名
    private String name;

    // 分数
    private Integer score;

    public TagBean() {
    }

    public TagBean(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    // 增加分数
    public void addScore(Integer num) {
        this.score = this.score + num;
    }

    @Override
    public int compareTo(TagBean o) {
        return o.getScore().compareTo(this.score);
    }

    public String getName() {
        return name;
    }

    public TagBean setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getScore() {
        return score;
    }

    public TagBean setScore(Integer score) {
        this.score = score;
        return this;
    }
}
