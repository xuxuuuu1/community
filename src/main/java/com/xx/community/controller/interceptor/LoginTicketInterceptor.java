package com.xx.community.controller.interceptor;

import com.xx.community.entity.LoginTicket;
import com.xx.community.entity.User;
import com.xx.community.service.UserService;
import com.xx.community.util.CookieUtil;
import com.xx.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //浏览器发过来的请求中从cookie中获取 ticket的值
        String ticket = CookieUtil.getValue(request,"ticket");
        //如果用户是登陆状态
        if (ticket != null) {
            //根据ticket查询LoginTicket
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            //验证LoginTicket的有效性 如果loginTicket不为空 且正在登录状态 且 登陆时间没有超时
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                //根据loginTicket查询user
                User user = userService.findUserById(loginTicket.getUserId());
                //在本次请求中持有user对象，只要这个用户没退出，则线程一直存在，工具类就会一直持有这个对象
                hostHolder.setUser(user);
            }
        }
        //表示不拦截
        return true;
    }


    //在controller之后，且执行完之后就会轮到模板引擎执行，
    //模板引擎将user数据渲染到页面上后返回给用户，此时是登陆成功之后的画面
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //获取当前线程的用户
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            modelAndView.addObject("loginUser",user);
        }
    }

    //在模板引擎渲染完之后执行 此时用户数据已经渲染到页面上可以清除数据
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
