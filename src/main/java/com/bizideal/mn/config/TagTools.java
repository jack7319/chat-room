/*
package com.bizideal.mn.config;

import com.bizideal.mn.controller.StringPointer;
import com.bizideal.mn.controller.TagBean;
import com.bizideal.mn.controller.TagNode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TagTools implements InitializingBean {

    // 数组大小
    private static final int DEFAULT_SIZE = 1024;

    // 内容分数
    private static final int CONTENT_SCORE = 1;

    // 标题分数
    private static final int TITLE_SCORE = 5;

    // 标签数量
    private static final int TAG_SIZE = 5;

    // 最低分数
    private static final int MIN_SCORE = 2;

    // 标签数组
    private final TagNode[] nodes = new TagNode[DEFAULT_SIZE];

    private final TagMapper tagMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 初始化标签库
        List<String> tags = tagMapper.selectAll();
        for (String tag : tags) {
            put(tag);
        }
    }

    */
/**
     * 自动生成标签
     *//*

    public void generateTags(String title, String content) {
        Map<String, TagBean> tagMap = new HashMap<>();
        matchTags(title, TagTools.TITLE_SCORE, tagMap);
        matchTags(content, TagTools.CONTENT_SCORE, tagMap);
        // 获取所有匹配到的标签名及分数
        Collection<TagBean> tagBeans = tagMap.values();
        // TODO 相关业务逻辑
    }

    */
/**
     * 匹配标签
     *//*

    private void matchTags(String content, Integer score, Map<String, TagBean> tagMap) {
        content = preHandle(content);
        StringPointer sp = new StringPointer(content);
        int i = 0;
        while (i < sp.length - 1) {
            int step = 1;
            int hash = sp.nextTwoCharHash(i);
            TagNode node = nodes[hash & (nodes.length - 1)];
            if (node != null) {
                int mix = sp.nextTwoCharMix(i);
                outer:
                for (; node != null; node = node.next) {
                    if (node.headTwoCharMix == mix) {
                        NavigableSet<StringPointer> desSet = node.words.headSet(sp.substring(i), true);
                        for (StringPointer word : desSet.descendingSet()) {
                            if (sp.nextStartsWith(i, word)) {
                                String tagWord = word.toString();
                                TagBean tag = tagMap.get(tagWord);
                                if (tag == null) {
                                    tagMap.put(tagWord, new TagBean(tagWord, score));
                                } else {
                                    tag.addScore(score);
                                }
                                step = word.length;
                                break outer;
                            }
                        }
                    }
                }
            }
            i += step;
        }
    }

    */
/**
     * 文本预处理
     * '-'转空格 -> 移除Html标签
     *//*

    private String preHandle(String content) {
        return content.replaceAll("-", " ").replaceAll("<.*?>", "");
    }

    */
/**
     * 新增标签节点
     *//*

    private void put(String word) {
        if (word == null || word.trim().length() < 2) {
            return;
        }
        StringPointer sp = new StringPointer(word.trim());
        int hash = sp.nextTwoCharHash(0);
        int mix = sp.nextTwoCharMix(0);
        int index = hash & (nodes.length - 1);
        TagNode node = nodes[index];
        if (node == null) {
            node = new TagNode(mix);
            node.words.add(sp);
            nodes[index] = node;
        } else {
            while (node != null) {
                if (node.headTwoCharMix == mix) {
                    node.words.add(sp);
                    return;
                }
                if (node.next == null) {
                    new TagNode(mix, node).words.add(sp);
                    return;
                }
                node = node.next;
            }
        }
    }

    public TagNode[] getNodes() {
        return nodes;
    }

    public TagMapper getTagMapper() {
        return tagMapper;
    }
}
*/
