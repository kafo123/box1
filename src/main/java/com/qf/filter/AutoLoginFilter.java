package com.qf.filter;

import com.qf.entity.User;
import com.qf.service.UserService;
import com.qf.service.impl.UserServiceImpl;
import com.qf.util.Base64Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author kafo
 * @description
 * @date 2020/9/20
 * @TIME 11:25
 */
@WebFilter("/*")
public class AutoLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        Object user = req.getSession().getAttribute("user");
        if(user!=null){
            chain.doFilter(req, resp);
            return;
        }else {
            Cookie[] cookies = req.getCookies();
            if(cookies!=null) {
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals("userinfo")) {
                        String[] split = cookie.getValue().split("#");
                        String uName = Base64Utils.decode(split[0]);
                        String uPassword = Base64Utils.decode(split[1]);
                        UserService userService = new UserServiceImpl();
                        try {
                            User login = userService.Login(uName, uPassword);
                            if (login != null) {
                                req.getSession().setAttribute("user", login);
                            } else {
                                resp.sendRedirect(req.getContextPath() + "/login.jsp");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else{
                resp.sendRedirect(req.getContextPath() + "/login.jsp");

            }

        }
        chain.doFilter(req, resp);

    }

    @Override
    public void destroy() {

    }
}
