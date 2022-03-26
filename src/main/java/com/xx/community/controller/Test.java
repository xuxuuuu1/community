package com.xx.community.controller;


import com.xx.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/hello")
public class Test {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/xx")
    @ResponseBody
    public String sayHello() {
        return "hello xx";
    }

    @RequestMapping("/access")
    @ResponseBody
    public String access() {
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http (HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" + value);
        }
        System.out.println(request.getParameter("code"));

        //返回响应数据
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write("<h1>小破网<h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            assert writer != null;
            writer.close();
        }
    }


    @RequestMapping(path = "/student/{id}")
    @ResponseBody
    public String test04(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    @RequestMapping("/student")
    @ResponseBody
    public String test05(String name,int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    @RequestMapping("/teacher")
    public ModelAndView test06() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age","30");
        mav.setViewName("/demo/view");
        return mav;
    }

    @RequestMapping("/emp")
    @ResponseBody
    public Map<String,Object> test07() {
        Map<String,Object> map = new HashMap<>();
        map.put("name","张三");
        map.put("age","23");
        map.put("salary",9000.00);
        return map;
    }
}
