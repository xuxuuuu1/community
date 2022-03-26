package com.xx.community;

import com.xx.community.dao.AlphaDao;
import com.xx.community.dao.DiscussPostMapper;
import com.xx.community.dao.UserMapper;
import com.xx.community.entity.DiscussPost;
import com.xx.community.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests implements ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private DiscussPostMapper discussPostMapper;



	//	@Test
//	void contextLoads() {
//	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Test
	public void test() {
		System.out.println(applicationContext);
		AlphaDao alphaDao = applicationContext.getBean(AlphaDao.class);
		System.out.println(alphaDao.select());

	}

	@Test
	public void test01() {
		SimpleDateFormat simpleDateFormat = applicationContext.getBean(SimpleDateFormat.class);
		System.out.println(simpleDateFormat.format(new Date()));
	}

	@Autowired
	private AlphaDao alphaDao;
	@Test
	public void test02() {
		System.out.println(alphaDao.select());
	}


	//mapper测试
	@Test
	public void test03() {
		User user = userMapper.selectById(11);
		System.out.println(user);
	}
	@Test
	public void test04() {
		List<DiscussPost> list = discussPostMapper.selectDiscussPosts(149, 0, 10);
		for (DiscussPost post : list) {
			System.out.println(post);
		}

		int rows = discussPostMapper.selectDiscussPostRows(149);
		System.out.println(rows);
	}



}
