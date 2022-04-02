package com.xx.community.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
//1 定义前缀树
//2 根据敏感词，初始化前缀树数据
//3 编写过滤敏感词方法

@Component
public class SensitiveFilter {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);
    //用于替换敏感词的符号
    private static final String REPLACEMENT = "***";
    private TrieNode rootNode = new TrieNode();
    //容器在调构造这个类之后 进行初始化，容器在服务初次启动时就会调用该方法
    //实例化bean -> 依赖注入 -> postConstruct
    //在servlet启动时调用一次
    //try后面括号中的语句最自动在finally中关闭
    @PostConstruct
    public void init() {
        try(
                //this.getClass().getClassLoader()获取到的是类加载路径->编译过后的classes文件中
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                //字节流 -> 字符流 -> 缓冲流
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                ){
                String keyword;
                //读取文本信息 不到最后一行不为空
                while ((keyword = reader.readLine()) != null) {
                    this.addKeyword(keyword);
                }
        } catch(IOException e) {
            logger.error("加载敏感词失败 ：" + e.getMessage());
        }
    }

    //将一个敏感词添加到前缀树中
    public void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            //如果子节点为空
            if (subNode == null) {
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }
            //指向子节点
            tempNode = subNode;
            //设置结束标识符
            if (i == keyword.length() - 1) {
                tempNode.setKeyWordEnd(true);
            }
        }
    }

    //过滤敏感字符 注意敏感词为fabcd abc这种情况检验fabc
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        //指针1 指向字典树
        TrieNode tempNode = rootNode;
        //指针2
        int begin = 0;
        //指针3
        int position = 0;
        //用于记录结果
        StringBuilder sb = new StringBuilder();
        //一个字符一个字符的比
        while (begin < text.length()) {
            char c = text.charAt(position);
            //跳过符号
            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    sb.append(c);
                    begin++;
                }
                //指针3无论如何都要向下走
                position++;
                continue;
            }
            //检查下级节点
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                //以begin开头的字符不是敏感字符
                sb.append(text.charAt(begin));
                //进入下一个字符
                position = ++begin;
                //重新指向根节点
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {//以begin开头的字符串失敏感字符串
                sb.append(REPLACEMENT);
                begin = ++position;
                tempNode = rootNode;
            } else {
                //检查下一个字符
                if (position < text.length() - 1) {
                    position++;
                }
            }
        }
        return sb.toString();
    }


    private boolean isSymbol(Character c) {
        //是普通字符则返回真
        //0x2E80到0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }


    //前缀树结点定义
    private class TrieNode {
        //判断是否是关键字结尾
        private boolean isKeywordEnd = false;
        //子节点的字符以及对应的结点
        private Map<Character,TrieNode> subNodes = new HashMap<>();
        //判断是否是关键字结尾
        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }
        public void setKeyWordEnd(boolean keyWordEnd) {
            isKeywordEnd = keyWordEnd;
        }
        //添加子节点
        public void addSubNode(Character c,TrieNode node) {
            subNodes.put(c,node);
        }
        //获取子节点
        public TrieNode getSubNode(Character c) {
            return subNodes.get(c);
        }

    }
}
