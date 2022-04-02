package com.xx.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommunityUtil {

    //生成随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
    //MD5加密 对密码加密
    // hello + 3e4a8 ->一个加密字符串就叫加盐
    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    //fastjson
    //post有三个参数
    //访问路径、向服务器提交的数据一个json对象
    //声明一个回调函数，浏览器得到服务器响应后会调用function
    public static String getJSONString(int code, String msg, Map<String,Object> map) {
        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("msg",msg);
        if (map != null) {
            for (String key : map.keySet()) {
                json.put(key,map.get(key));
            }
        }
        return json.toJSONString();
    }
    public static String getJSONString(int code,String msg) {
        return getJSONString(code,msg,null);
    }

    public static String getJSONString(int code) {
        return getJSONString(code,null,null);
    }
    //用于测试 看json格式 ajax发用异步请求
    public static void main(String[] args) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","zhangsan");
        map.put("age","21");
        System.out.println(getJSONString(0,"ok",map));
    }

}
