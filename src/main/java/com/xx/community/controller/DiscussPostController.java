package com.xx.community.controller;

import com.xx.community.dao.DiscussPostMapper;
import com.xx.community.entity.Comment;
import com.xx.community.entity.DiscussPost;
import com.xx.community.entity.Page;
import com.xx.community.entity.User;
import com.xx.community.service.CommentService;
import com.xx.community.service.UserService;
import com.xx.community.util.CommunityConstant;
import com.xx.community.util.CommunityUtil;
import com.xx.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

//1 前端页面点击发布帖子后 把数据传到controller
//2 判断用户是否是登录状态，如果是登录状态才能发布帖子(设置如果不是登录状态则不显示发布帖子)
//3 前端页面主要发送content和title给后端，调用discussPostService的insert功能将数据插入到数据库

@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements CommunityConstant {

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    //用于ajax请求
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title,String content) {
        User user = hostHolder.getUser();
        if (user == null) {
            return CommunityUtil.getJSONString(403,"你还没有登录");
        }
        //切记:设置的是用户的id
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setContent(content);
        post.setTitle(title);
        post.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(post);

        //报错情况 再说
        return CommunityUtil.getJSONString(0,"发布成功");
    }


    //查看帖子详情功能
    //1 前端配置好访问路径，一旦在index界面点击，就发送请求给下面这个controller
    //2 在controller中，利用service调用mapper查询到数据库中帖子的详情，根据帖子的user_id字段查询到user
    //  将user和post添加到model中供模板渲染页面
    //3 转发到/site/discuss-detail渲染并返回给用户

    @RequestMapping(path = "/detail/{discussPostId}",method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId, Model model, Page page) {
        //帖子
        DiscussPost post = discussPostMapper.selectDiscussPostById(discussPostId);
        model.addAttribute("post",post);
        //作者
        int userId = post.getUserId();
        User user = userService.findUserById(userId);
        model.addAttribute("user",user);

        //评论分页信息
        //每个实体下面5条评论
        page.setLimit(5);
        page.setPath("/discuss/detail/" + discussPostId);
        page.setRows(post.getCommentCount());
//
//        //评论：给帖子的评论
//        //回复：给评论的评论
//        //评论列表
        List<Comment> commentList = commentService.findCommentsByEntity(
                ENTITY_TYPE_POST,post.getId(),page.getOffset(),page.getLimit());
        //评论vo列表 vo表示view object
        List<Map<String,Object>> commentVoList = new ArrayList<>();
        if (commentList != null) {
            //遍历帖子的评论
            for (Comment comment : commentList) {
                //评论Vo
                Map<String,Object> commentVo = new HashMap<>();
                //评论
                commentVo.put("comment",comment);
                //作者
                commentVo.put("user",userService.findUserById(comment.getUserId()));

                //回复列表
                List<Comment> replyList = commentService.findCommentsByEntity(
                        ENTITY_TYPE_COMMENT,comment.getId(),0,Integer.MAX_VALUE
                );
                //回复Vo列表
                List<Map<String,Object>> replyVoList = new ArrayList<>();
                if (replyList != null) {
                    for (Comment reply : replyList) {
                        Map<String,Object> replyVo = new HashMap<>();
                        //回复
                        replyVo.put("reply",reply);
                        //作者
                        replyVo.put("user",userService.findUserById(reply.getUserId()));
                        //回复目标
                        User target = reply.getTargetId() == 0 ? null : userService.findUserById(reply.getTargetId());
                        replyVo.put("target",target);

                        replyVoList.add(replyVo);
                    }
                }
                commentVo.put("replys",replyVoList);
                //回复数量
                int replyCount = commentService.findCommentCount(ENTITY_TYPE_COMMENT, comment.getId());
                commentVo.put("replyCount",replyCount);
                commentVoList.add(commentVo);
            }
        }

        model.addAttribute("comments",commentVoList);

        return "/site/discuss-detail";
    }
}
