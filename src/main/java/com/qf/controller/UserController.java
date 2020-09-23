package com.qf.controller;

import cn.dsna.util.images.ValidateCode;
import com.qf.annotation.ContentType;
import com.qf.entity.User;
import com.qf.service.UserService;
import com.qf.service.impl.UserServiceImpl;
import com.qf.util.ActiveCodeUtil;
import com.qf.util.Base64Utils;
import com.qf.util.SysContant;
import org.apache.commons.beanutils.BeanUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kafo
 * @description
 * @date 2020/9/14
 * @TIME 19:57
 */
@WebServlet("/userController")
public class UserController extends BaseController  {

    public String  register(HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IllegalAccessException, SQLException, UnknownHostException {
        User user = new User();
            BeanUtils.populate(user, req.getParameterMap());
            user.setuStatus(SysContant.NO_ACTIVE);
            user.setuCode(ActiveCodeUtil.getActiveCode());
            user.setuRole(SysContant.CUSTOMER);
            UserService userService = new UserServiceImpl();
            int i = userService.register(user);
            if(i>0){
                //resp.sendRedirect(req.getContextPath()+"/registerSuccess.jsp");
                return SysContant.REDIRECT+SysContant.FLAG+"registerSuccess.jsp";
            }else{
                req.setAttribute("registerMsg", "注册失败");
                //req.getRequestDispatcher("/register.jsp").forward(req, resp);
                return SysContant.FORWORD+SysContant.FLAG+"login.jsp";
            }
    }

    public String login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String code = req.getParameter("code");
        String sysCode = (String) req.getSession().getAttribute("code");
        if (!sysCode.equalsIgnoreCase(code)) {
            req.setAttribute("msg", "验证码不正确");
            //req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return SysContant.FORWORD+SysContant.FLAG+"login.jsp";
        }
        String uName = req.getParameter("uName");
        String uPassword = req.getParameter("uPassword");
            UserService userService = new UserServiceImpl();
            User login = userService.Login(uName, uPassword);
            if (login != null) {
                if (login.getuStatus() == SysContant.NO_ACTIVE) {
                    req.setAttribute("msg", "账号未激活");
                    //req.getRequestDispatcher("/login.jsp").forward(req, resp);
                    return SysContant.FORWORD+SysContant.FLAG+"login.jsp";
                }
                if (login.getuStatus() == SysContant.ACTIVE) {
                    req.getSession().setAttribute("user", login);
                    Cookie cookie = new Cookie("userinfo", Base64Utils.encode(login.getuName())+"#"+Base64Utils.encode(login.getuPassword()));
                    cookie.setMaxAge(60*60*24*14);
                    cookie.setHttpOnly(true);
                    cookie.setPath("/");
                    resp.addCookie(cookie);
                    //req.getRequestDispatcher("/index.jsp").forward(req, resp);
                    return SysContant.FORWORD+SysContant.FLAG+"index.jsp";
                }
            } else {
                req.setAttribute("msg", "用户名或密码错误");
                //req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return SysContant.REDIRECT+SysContant.FLAG+"login.jsp";
            }
        return SysContant.REDIRECT+SysContant.FLAG+"index.jsp";
    }
    @ContentType("image/jpg")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ValidateCode validateCode = new ValidateCode(100, 50, 4, 10) ;
        String code = validateCode.getCode();
        request.getSession().setAttribute("code", code);
        ImageIO.write(validateCode.getBuffImg(), "jpg", response.getOutputStream());
    }
    @ContentType("application/json;charset=utf-8")
    public Map<String,Integer> checkName(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String username = request.getParameter("username");
        UserService userService = new UserServiceImpl();
        boolean f = userService.checkName(username);
        Map<String,Integer> map = new HashMap<>();
        if(f){
           map.put("data",0) ;
        }else {
            map.put("data", 1);
        }
        return map;
    }

    public String  activeAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String c = request.getParameter("c");
        String code = new String(Base64.getDecoder().decode(c));
        UserService userService = new UserServiceImpl();
        int i = userService.activeAccount(code);
        if(i>0){
            request.setAttribute("msg", "激活成功，您可以登录了");
        }else{
            request.setAttribute("msg", "激活失败");
        }
        return SysContant.FORWORD+SysContant.FLAG+"message.jsp";
    }

    public String logOut(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
        return SysContant.REDIRECT+SysContant.FLAG+"login.jsp";
    }
}
