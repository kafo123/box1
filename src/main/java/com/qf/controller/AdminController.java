package com.qf.controller;

import com.qf.annotation.ContentType;
import com.qf.entity.User;
import com.qf.service.AdminService;
import com.qf.service.AdminServiceImpl;
import com.qf.service.UserService;
import com.qf.service.impl.UserServiceImpl;
import com.qf.util.SysContant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/18
 * @TIME 21:26
 */
@WebServlet("/adminController")
public class AdminController extends BaseController {
    public String login(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AdminService adminService = new AdminServiceImpl();
        User admin = adminService.login(username, password);
        if(admin!=null){
            request.getSession().setAttribute("admin", admin);
            return SysContant.REDIRECT+SysContant.FLAG+"admin/admin.jsp";
        }else {
            return SysContant.REDIRECT+SysContant.FLAG+"admin/login.jsp";
        }

    }
    @ContentType("application/json;charset=utf-8")
    public List<User> getAllUser(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        UserService userService = new UserServiceImpl();
        List<User> allUser = userService.getAllUser();
        return allUser;
    }
    @ContentType("application/json;charset=utf-8")
    public int deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        UserService userService = new UserServiceImpl();
        int i = userService.removeUserByUid(id);
        if(i>0){
            return 1;
        }
        return 0;
    }
    @ContentType("application/json;charset=utf-8")
    public List<User> searchUser(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String username = request.getParameter("username");
        String gender = request.getParameter("gender");
        UserService userService = new UserServiceImpl();
        List<User> users= userService.findUsersByNameAndSex(username, gender);
        return users;
    }
    @ContentType("application/json;charset=utf-8")
    public List<User> getInvalidUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException{
        UserService userService = new UserServiceImpl();
        List<User> invalidUsers = userService.getInvalidUsers();
        return invalidUsers;
    }
    @ContentType("application/json;charset=utf-8")
    public int activeUser(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        UserService userService = new UserServiceImpl();
        int i = userService.activeValidUser(id);
        return i;
    }
    
}
