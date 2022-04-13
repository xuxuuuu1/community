package com.xx.community.controller;

import com.xx.community.entity.Event;
import com.xx.community.entity.User;
import com.xx.community.event.EventProducer;
import com.xx.community.service.LikeService;
import com.xx.community.util.CommunityConstant;
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
public class LikeController implements CommunityConstant {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    /**
     *
     * @param entityType 帖子的类型 评论还是帖子
     * @param entityId 帖子的id
     * @param entityUserId 帖子的作者的id
     * @return
     */
    //ajax请求，用post，返回字符串实时响应
    @RequestMapping(value = "/like",method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId, int postId) {
        User user = hostHolder.getUser();

        likeService.like(user.getId(), entityType, entityId, entityUserId);

        long likeCount = likeService.findEntityLikeCount(entityType, entityId);

        int likeStatus = likeService.findEntityLikeStatus(user.getId(),entityType,entityId);

        Map<String, Object> map = new HashMap<>();
        map.put("likeCount",likeCount);
        map.put("likeStatus",likeStatus);

        // 触发点赞事件
        if (likeStatus == 1) {
            Event event = new Event()
                    .setTopic(TOPIC_LIKE)
                    .setUserId(hostHolder.getUser().getId())
                    .setEntityType(entityType)
                    .setEntityId(entityId)
                    .setEntityUserId(entityUserId)
                    .setData("postId", postId);
            eventProducer.fireEvent(event);
        }
        return CommunityUtil.getJSONString(0, null, map);
    }
}
