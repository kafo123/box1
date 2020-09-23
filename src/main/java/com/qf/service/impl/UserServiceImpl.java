package com.qf.service.impl;

import com.qf.dao.UserDao;
import com.qf.dao.impl.UserDaoImpl;
import com.qf.entity.User;
import com.qf.service.UserService;
import com.qf.util.EmailUtils;
import com.qf.util.StringUtil;
import com.qf.util.SysContant;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/14
 * @TIME 19:52
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean checkName(String name) throws SQLException {
        return userDao.findByUsername(name)!=null;
    }

    @Override
    public User Login(String username, String password) throws SQLException {
        User user = userDao.findByUsername(username);
        if(user!=null){
            if(user.getuPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    @Override
    public int register(User user) throws SQLException, UnknownHostException {
        int i = userDao.insertUser(user);
        if(i>0){
            String title = "小米商城激活账户";
            String ip = Inet4Address.getLocalHost().getHostAddress();
            //String ip = "10.9.63.245";
            String c = Base64.getEncoder().encodeToString(user.getuCode().getBytes());
            System.out.println(c);
            String url = "http://"+ip+":8080/MiShop/userController?method=activeAccount&c="+c;
            String content = user.getuName() + ":<br>您好,<a href='"+url+ "'>请点击该链接激活账户</a>";
            EmailUtils.sendEmail(title,content,user.getuEmail());
        }
        return i;
    }
    @Override
    public int activeAccount(String code) throws SQLException {
        int statusByCode = userDao.findStatusByCode(code);
        if(statusByCode== SysContant.NO_ACTIVE){
            int i = userDao.updateStatusAndCodeByCode(code);
            return i;
        }
        return 0;
    }

    @Override
    public List<User> getAllUser() throws SQLException {
        return userDao.findAllUser();
    }

    @Override
    public int removeUserByUid(int uid) throws SQLException {
        return userDao.deleteUser(uid);
    }

    @Override
    public List<User> findUsersByNameAndSex(String uName, String uSex) throws SQLException {
        StringBuilder sql = new StringBuilder("select u_id as uId,u_name as uName,u_email as uEmail,u_sex as uSex,u_role as uRole from user where 1=1");
        if(!StringUtil.isEmpty(uName)){
            sql.append("   and u_name like '%"+uName+"%'");
        }
        if(!StringUtil.isEmpty(uSex)){
            sql.append("   and u_sex='"+uSex+"'");
        }
        return userDao.findUsersByNameAndSex(sql.toString());
    }

    @Override
    public List<User> getInvalidUsers() throws SQLException {
        return userDao.findInvalidUsers();
    }

    @Override
    public int activeValidUser(int uId) throws SQLException {
        return userDao.updateStatus(uId);
    }
}
