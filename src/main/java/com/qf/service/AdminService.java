package com.qf.service;

import com.qf.entity.User;

import java.sql.SQLException;

/**
 * @author kafo
 * @description
 * @date 2020/9/18
 * @TIME 21:17
 */
public interface AdminService {

    User login(String uName, String uPassword) throws SQLException;
}
