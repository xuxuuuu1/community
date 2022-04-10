package com.xx.community.controller;

import com.xx.community.entity.DiscussPost;
import com.xx.community.entity.Page;
import com.xx.community.entity.User;
import com.xx.community.service.DiscussPostService;
import com.xx.community.service.LikeService;
import com.xx.community.service.UserService;
import com.xx.community.util.CommunityConstant;
import com.xx.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController implements CommunityConstant {

    @Autowired
    private UserService userService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(path = "/index",method = RequestMethod.GET)
    //在参数中 springMVC会自动将page model装配到容器中
    //所以在最后不用addAttribute
    public String getIndexPage(Model model, Page page) {
        //获取所有的帖子
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.findDiscussPosts(0,page.getOffset(),page.getLimit());
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPost post : list) {
                Map<String,Object> map = new HashMap<>();
                map.put("post",post);
                User user = userService.findUserById(post.getUserId());
                map.put("user",user);

                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST,post.getId());
                map.put("likeCount",likeCount);

                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";
    }


    //返回错误页面
    @RequestMapping(path = "/error",method = RequestMethod.GET)
    public String getErrorPage() {
        return "/error/500";
    }

}
