package com.xx.community;

import com.xx.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTests {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void test01() {
        mailClient.sendMail("1726549405@qq.com","test","test");
        System.out.println("发送成功");
    }

    @Test
    public void test02() {
        Context context = new Context();
        context.setVariable("username","徐兴");

        String content = templateEngine.process("/mail/demo",context);

        System.out.println(content);

        mailClient.sendMail("1726549405@qq.com","test",content);
    }
}
