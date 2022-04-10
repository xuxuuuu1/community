package com.xx.community;

import com.xx.community.dao.DiscussPostMapper;
import com.xx.community.entity.DiscussPost;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class discussPostTest {

    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Test
    public void test01() {
        DiscussPost post = new DiscussPost();
        post.setTitle("特大新闻");
        post.setCommentCount(10);
        post.setContent("据说。。。。。。");
        post.setUserId(100);
        int i = discussPostMapper.insertDiscussPost(post);
        System.out.println(i);
    }
    @Test
    public void test02(){
        String fileName = "www.png";
        System.out.println(fileName.lastIndexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
    }
}
