package com.xx.community.controller;

import com.xx.community.entity.Comment;
import com.xx.community.entity.DiscussPost;
import com.xx.community.entity.Event;
import com.xx.community.event.EventProducer;
import com.xx.community.service.CommentService;
import com.xx.community.service.DiscussPostService;
import com.xx.community.util.CommunityConstant;
import com.xx.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = "/add/{discussPostId}",method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment) {
        comment.setUserId(hostHolder.getUser().getId());
        comment.setCreateTime(new Date());
        comment.setStatus(0);
        commentService.addComment(comment);

        Event event = new Event()
                .setTopic(TOPIC_COMMENT)
                .setUserId(hostHolder.getUser().getId())
                .setEntityType(comment.getEntityType())
                .setEntityId(comment.getEntityId())
                .setData("postId",discussPostId);

        // 操作针对的目标 对谁点赞 对谁评论
        if (comment.getEntityType() == ENTITY_TYPE_POST) {
            DiscussPost target = discussPostService.findDiscussPost(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        } else if (comment.getEntityType() == ENTITY_TYPE_COMMENT) {
            Comment target = commentService.findCommentById(comment.getId());
            event.setEntityUserId(target.getUserId());
        }
        //并发处理
        eventProducer.fireEvent(event);
        //跳转到帖子的页面
        return "redirect:/discuss/detail/" + discussPostId;
    }
}
