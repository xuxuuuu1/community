package com.xx.community;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test01() {
        String redisKey = "test:count";
        redisTemplate.opsForValue().set(redisKey, 1);
        System.out.println(redisTemplate.opsForValue().get(redisKey));
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));
    }

    //访问hash
    @Test
    public void test02() {
        String redisKey = "test:user";
        redisTemplate.opsForHash().put(redisKey, "id", 1);
        redisTemplate.opsForHash().put(redisKey, "name", "张三");

        System.out.println(redisTemplate.opsForHash().get(redisKey, "id"));
        System.out.println(redisTemplate.opsForHash().get(redisKey, "name"));

    }

    @Test
    public void test03() {
        String redisKey = "test:ids";
        //103 102 101
        redisTemplate.opsForList().leftPush(redisKey, 101);
        redisTemplate.opsForList().leftPush(redisKey, 102);
        redisTemplate.opsForList().leftPush(redisKey, 103);
        System.out.println(redisTemplate.opsForList().range(redisKey, 0, 2));
        System.out.println(redisTemplate.opsForList().size(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));

    }

    @Test
    public void test04() {
        String redisKey = "test:teachers";

        redisTemplate.opsForSet().add(redisKey, "张飞","关羽","刘备");

        System.out.println(redisTemplate.opsForSet().size(redisKey));
        System.out.println(redisTemplate.opsForSet().pop(redisKey));
        System.out.println(redisTemplate.opsForSet().pop(redisKey));
    }

    @Test
    public void test05() {
        String redisKey = "test:student";


        redisTemplate.opsForZSet().add(redisKey,"张三",10);
        redisTemplate.opsForZSet().add(redisKey,"李四",20);
        redisTemplate.opsForZSet().add(redisKey,"王五",30);
        redisTemplate.opsForZSet().add(redisKey,"赵六",40);
        redisTemplate.opsForZSet().add(redisKey,"嘛七",50);

        System.out.println(redisTemplate.opsForZSet().range(redisKey,0,2));
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey,0,2));
        System.out.println(redisTemplate.opsForZSet().score(redisKey,"张三"));
    }


    //绑定
    @Test
    public void test06() {
        String redisKey = "test:count";

        BoundValueOperations ops = redisTemplate.boundValueOps(redisKey);

        System.out.println(ops.get());
        System.out.println(ops.increment());
        System.out.println(ops.increment());
        System.out.println(ops.increment());
        System.out.println(ops.increment());
        System.out.println(ops.increment());
        System.out.println(ops.increment());
        System.out.println(ops.get());
    }

    //编程式事务
    @Test
    public void test07() {
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String redisKey = "test:niu";
                //开始事务
                operations.multi();
                //将下列语句放入队列 在exec之后才会执行添加语句
                operations.opsForSet().add(redisKey,"张三");
                operations.opsForSet().add(redisKey,"lisi");
                operations.opsForSet().add(redisKey,"wangwu");
                System.out.println(operations.opsForSet().members(redisKey));

                return operations.exec();
            }
        });
        System.out.println(obj);
    }
}
