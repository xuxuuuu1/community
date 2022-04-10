package com.xx.community.controller;

import com.xx.community.entity.User;
import com.xx.community.service.LikeService;
import com.xx.community.util.CommunityUtil;
import com.xx.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    //ajax请求，用post，返回字符串实时响应
    @RequestMapping(value = "/like",method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId) {
        User user = hostHolder.getUser();

        likeService.like(user.getId(), entityType, entityId);

        long likeCount = likeService.findEntityLikeCount(entityType, entityId);

        int status = likeService.findEntityLikeStatus(user.getId(),entityType,entityId);

        Map<String, Object> map = new HashMap<>();
        map.put("likeCount",likeCount);
        map.put("likeStatus",status);
        return CommunityUtil.getJSONString(0, null, map);
    }
}
