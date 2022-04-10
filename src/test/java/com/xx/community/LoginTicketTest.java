package com.xx.community;

import com.xx.community.dao.LoginTicketMapper;
import com.xx.community.entity.LoginTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class LoginTicketTest {
    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void test01() {
//        LoginTicket loginTicket = new LoginTicket();
//        loginTicket.setTicket("abc");
//        loginTicket.setExpired(new Date());
//        loginTicket.setStatus(1);
//        loginTicket.setUserId(170);
//
//        int i = loginTicketMapper.insertLoginTicket(loginTicket);
//        System.out.println(i);
//        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
//        System.out.println(loginTicket);
        int abc = loginTicketMapper.updateStatus("abc", 1);
        System.out.println(abc);
    }

}
