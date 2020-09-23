package com.qf.service;

import com.qf.dao.UserDao;
import com.qf.dao.impl.UserDaoImpl;
import com.qf.entity.User;
import com.qf.util.SysContant;

import java.sql.SQLException;

/**
 * @author kafo
 * @description
 * @date 2020/9/18
 * @TIME 21:20
 */
public class AdminServiceImpl implements AdminService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String uName, String uPassword) throws SQLException {
        User user = userDao.findByUsername(uName);
        if (user != null) {
            if (user.getuRole() == SysContant.ADMIN&&user.getuPassword().equals(uPassword)) {
                return user;
            }
        }
        return null;
    }
}
