package com.xx.community.controller;

import com.xx.community.annotation.LoginRequired;
import com.xx.community.entity.User;
import com.xx.community.service.FollowService;
import com.xx.community.util.CommunityUtil;
import com.xx.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FollowController {
    @Autowired
    private FollowService followService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(value = "/follow",method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public String follow(int entityType, int entityId) {
        User user = hostHolder.getUser();

        followService.follow(user.getId(), entityType, entityId);
        return CommunityUtil.getJSONString(0,"已关注");
    }

    @RequestMapping(value = "/unfollow",method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public String unfollow(int entityType, int entityId) {
        User user = hostHolder.getUser();

        followService.unfollow(user.getId(), entityType, entityId);
        return CommunityUtil.getJSONString(0,"已取消关注");
    }
}
