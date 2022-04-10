package com.xx.community;


import com.xx.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class FilterTest {

    @Autowired
    private SensitiveFilter sensitiveFilter;
    @Test
    public void test01() {
        String text = "这里可以开票";
        String s = sensitiveFilter.filter(text);
        System.out.println(s);
    }
    @Test
    public void test02() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");

        try {
            assert is != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
                String s;
                while ((s = reader.readLine()) != null) {
                    System.out.println(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
