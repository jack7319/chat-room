package com.bizideal.mn.mapper;

import com.bizideal.mn.entity.Group;
import com.bizideal.mn.entity.UserInfo;
import io.netty.channel.Channel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : liulq
 * @date: 创建时间: 2018/1/19 11:06
 * @version: 1.0
 * @Description:
 */
public class ChannelManager {

    public static final AtomicInteger uidGener = new AtomicInteger(1000);
    public static final AtomicInteger gidGener = new AtomicInteger(1000);

    public static final List<UserInfo> users = new LinkedList<>(); // 存储所有的用户
    public static final Map<Integer, UserInfo> userIdUserInfo = new ConcurrentHashMap<>(); // 通过用户ID查询用户
    public static final List<Group> groups = new LinkedList<>(); // 存储所有的群组
    public static final Map<Integer, Group> groupIdGrups = new ConcurrentHashMap<>(); // 通过用户ID查询用户

    public static final Map<Integer, Channel> userIdChannel = new ConcurrentHashMap<>(); // 每个用户对应的连接
    public static final Map<Integer, DefaultChannelGroup> groupIdChannel = new ConcurrentHashMap<>(); // 每个群组对应的用户连接

    // 用户进入系统。添加连接
    public static void addConnection(Integer userId, Channel channel) {
        userIdChannel.put(userId, channel);
    }

    // 用户退出系统。删除连接
    public static void removeConnection(Integer userId) {
        if (userIdChannel.containsKey(userId)) {
            userIdChannel.remove(userId);
        }
    }

    // 创建用户
    public static int createUser(UserInfo user) {
        int userId = uidGener.getAndIncrement();
        user.setId(userId);
        users.add(user);
        userIdUserInfo.put(userId, user);
        return userId;
    }

    // 创建聊天室
    public static void createGroup(Group group) {
        int groupId = gidGener.getAndIncrement();
        group.setId(groupId);
        groups.add(group);
        groupIdGrups.put(groupId, group);
        DefaultChannelGroup channels = new DefaultChannelGroup(group.getName(), GlobalEventExecutor.INSTANCE);
        groupIdChannel.put(groupId, channels);
    }

    // 用户进入某一个聊天室
    public static void enterChatRoom(Integer userId, Integer groupId) {
        Group group = groupIdGrups.get(groupId);
        List<UserInfo> userInfoList = group.getUserInfoList();
        userInfoList.add(userIdUserInfo.get(userId));
    }

    // 用户与某一聊天室建立连接
    public static void addGroupConnection(Integer userId, Integer groupId) {
        DefaultChannelGroup channels = groupIdChannel.get(groupId);
        if (channels != null) {
            channels.add(userIdChannel.get(userId));
        }
    }

    // 用户退出某一个聊天室
    public static void removeGroupConnection(Integer userId, Integer groupId) {
        UserInfo userInfo = userIdUserInfo.get(userId);
        Group group = groupIdGrups.get(groupId);
        group.getUserInfoList().remove(userInfo);
        DefaultChannelGroup channels = groupIdChannel.get(groupId);
        if (channels != null) {
            channels.remove(userIdChannel.get(userId));
        }
    }
}
