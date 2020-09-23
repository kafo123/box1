package com.qf.service;

import com.qf.entity.User;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/14
 * @TIME 19:51
 */
public interface UserService {
    User Login(String username, String password) throws SQLException;
    int register(User user) throws SQLException, UnknownHostException;
    boolean checkName(String name) throws SQLException;
    int activeAccount(String code) throws SQLException;

    //-------------------------------------管理员服务--------------------
    List<User> getAllUser() throws SQLException;

    int removeUserByUid(int uid) throws SQLException;

    List<User> findUsersByNameAndSex(String uName, String uSex) throws SQLException;

    List<User> getInvalidUsers() throws SQLException;

    int activeValidUser(int uId) throws SQLException;
}
