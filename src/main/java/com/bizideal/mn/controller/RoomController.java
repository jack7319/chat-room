package com.bizideal.mn.controller;

import com.bizideal.mn.entity.Group;
import com.bizideal.mn.entity.UserInfo;
import com.bizideal.mn.mapper.ChannelManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author : liulq
 * @date: 创建时间: 2018/1/19 10:22
 * @version: 1.0
 * @Description:
 */
@Controller
@RequestMapping("/")
public class RoomController {

    @RequestMapping("/")
    public String login() {
        return "login";
    }

    @RequestMapping("/showMarkLayer")
    @ResponseBody
    public String showMarkLayer() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        return "success";
    }

    @RequestMapping("/toPage/{page}")
    public String toPage(@PathVariable String page) {
        return page;
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(String name, HttpSession session) {
        // 创建用户
        UserInfo userInfo = new UserInfo().setName(name);
        ChannelManager.createUser(userInfo);
        session.setAttribute("userInfo", userInfo);
        return "1";
    }

    // 获取所有的群组
    @RequestMapping("/getAllGroups")
    @ResponseBody
    public List<Group> getAllGroups() {
        return ChannelManager.groups;
    }

    // 获取群组中的群成员
    @RequestMapping("/getUserInfosByGroupId/{groupId}")
    @ResponseBody
    public List<UserInfo> getUserInfosByGroupId(@PathVariable Integer groupId) {
        Group group = ChannelManager.groupIdGrups.get(groupId);
        return group.getUserInfoList();
    }

    // 创建群组
    @RequestMapping("/createGroup")
    @ResponseBody
    public String createGroup(Group group) {
        ChannelManager.createGroup(group);
        return "1";
    }

    // 跳转进入群组
    @RequestMapping("/enterGroup/{groupId}")
    public String createGroup(@PathVariable Integer groupId, HttpSession session, Model model) {
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        ChannelManager.enterChatRoom(userInfo.getId(), groupId);
        Group group = ChannelManager.groupIdGrups.get(groupId);
        model.addAttribute("group", group);
        return "room";
    }

}
