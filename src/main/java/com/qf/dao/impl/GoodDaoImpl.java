package com.qf.dao.impl;

import com.qf.dao.GoodDao;
import com.qf.entity.Type;
import com.qf.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/15
 * @TIME 16:03
 */
public class GoodDaoImpl implements GoodDao {
    QueryRunner qr = new QueryRunner();
    @Override
    public List<Type> findAllType() throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "select t_id as tId,t_name as tName,t_info as tInfo from type";
        try {
            List<Type> allType = qr.query(connection, sql, new BeanListHandler<Type>(Type.class));
            return allType;
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public int insertType(Type type) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "insert into type(t_name,t_info) value(?,?)";
        try {
            return qr.update(connection, sql,type.gettName(),type.gettInfo());
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }
}
