package com.qf.dao.impl;

import com.qf.dao.UserDao;
import com.qf.entity.User;
import com.qf.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/14
 * @TIME 19:44
 */
public class UserDaoImpl implements UserDao {
    QueryRunner qr = new QueryRunner();
    @Override
    public User findByUsername(String username) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "select u_id as uId, u_name as uName,u_password as uPassword,u_status as uStatus,u_role as uRole from user where u_name=? ";
        try {
            return qr.query(connection, sql, new BeanHandler<User>(User.class), username);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public int insertUser(User user) throws SQLException {
        String sql = "insert into user(u_name,u_password,u_email,u_sex,u_status,u_code,u_role) values(?,?,?,?,?,?,?)";
        Connection connection = DruidUtils.getConnection();
        Object params[] = {user.getuName(),user.getuPassword(),user.getuEmail(),user.getuSex(),user.getuStatus(),user.getuCode(),user.getuRole()};
        try {
            return qr.update(connection, sql,params);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }



    @Override
    public int updateStatusAndCodeByCode(String code) throws SQLException {
        String sql = "update user set u_status=1 , u_code=null where u_code=?";
        Connection connection = DruidUtils.getConnection();
        int i;
        try {
            i = qr.update(connection, sql, code);
            return i;
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }

    }

    @Override
    public int findStatusByCode(String code) throws SQLException{
        String sql = "select u_status from user where u_code=?";
        Connection connection = DruidUtils.getConnection();
        Object query = null;
        try {
            query = qr.query(connection, sql, new ScalarHandler(), code);
            if(query==null){
                return -1;
            }else {
                return (Integer)query;
            }
        } finally {
             DruidUtils.closeAll(null, null, connection);
        }

    }


    //---------------------------------管理员----------------------------------------


    @Override
    public List<User> findAllUser() throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "select u_id as uId,u_name as uName,u_email as uEmail,u_sex as uSex,u_role as uRole from user ";
        return qr.query(connection, sql, new BeanListHandler<>(User.class));
    }

    @Override
    public int deleteUser(int uid) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "delete from user where u_id=?";
        try {
            return qr.update(connection, sql, uid);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }

    }

    @Override
    public List<User> findUsersByNameAndSex(String sql) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        //StringBuilder sql = new StringBuilder("select u_id as uId,u_name as uName,u_email as uEmail,u_sex as uSex,u_role as uRole from user where 1=1");
        try {
            return qr.query(connection, sql, new BeanListHandler<>(User.class));
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public List<User> findInvalidUsers() throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "select u_id as uId,u_name as uName,u_email as uEmail,u_sex as uSex,u_role as uRole from user where u_status=0 ";
        try {
            return qr.query(connection, sql,  new BeanListHandler<>(User.class));
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public int updateStatus(int uId) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "update user set u_status=1 where u_id="+uId;
        try {
            return qr.update(connection, sql);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }
}
