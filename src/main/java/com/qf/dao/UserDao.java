package com.qf.dao;

import com.qf.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description 用户表操作
 * @date 2020/9/14
 * @TIME 19:42
 */
public interface UserDao {

    /**
     * 查询用户信息
     *
     * @param username 用户名
     * @return User 包括 用户id,用户名，密码，用户激活状态，角色
     * @throws SQLException
     */
    User findByUsername(String username) throws SQLException;

    /**
     * 插入用户
     *
     * @param user
     * @return
     * @throws SQLException
     */
    int insertUser(User user) throws SQLException;

    /**
     * 查询用户的激活状态
     *
     * @param code 激活码
     * @return
     * @throws SQLException
     */
    int findStatusByCode(String code) throws SQLException;

    /**
     * 将用户的激活状态设为1，激活码清空
     *
     * @param code 激活码
     * @return
     * @throws SQLException
     */
    int updateStatusAndCodeByCode(String code) throws SQLException;

    /**
     * 查询所有用户的用户id，用户名，用户邮箱，性别，角色
     *
     * @return 所有用户信息的列表
     * @throws SQLException
     */
    List<User> findAllUser() throws SQLException;

    /**
     * 删除用户
     *
     * @param uid 用户id
     * @return
     * @throws SQLException
     */
    int deleteUser(int uid) throws SQLException;

    /**
     * 通过用户名和性别动态查找用户
     *
     * @param sql 动态SQL语句
     * @return
     * @throws SQLException
     */
    List<User> findUsersByNameAndSex(String sql) throws SQLException;

    /**
     * 查询未激活用户
     *
     * @return
     * @throws SQLException
     */
    List<User> findInvalidUsers() throws SQLException;

    /**
     * 将u_status设为1
     *
     * @param uId 用户id
     * @return
     */
    int updateStatus(int uId) throws SQLException;
}
