package com.qf.dao.impl;

import com.qf.dao.AddressDao;
import com.qf.entity.Address;
import com.qf.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 10:34
 */
public class AddressDaoImpl implements AddressDao {
    QueryRunner qr = new QueryRunner();
    @Override
    public List<Address> findAllAddress(int uid) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "select a_id as aId,u_id as uId,a_name as aName,a_phone as aPhone,a_detail as aDetail,a_state as aState from address where u_id=?";
        try {
            return qr.query(connection, sql, new BeanListHandler<>(Address.class), uid);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public int insertAddress(Address address) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "insert into address(u_id,a_name,a_phone,a_detail,a_state) value(?,?,?,?,?)";
        try {
            return qr.update(connection, sql,address.getuId(),address.getaName(),address.getaPhone(),address.getaDetail(),address.getaState());
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public int deleteAddress(int aid) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "delete from address where a_id=?";
        try {
            return qr.update(connection, sql,aid);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public int updateAddressStateByAid(int aid, int aState) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "update address set a_state=? where a_id=?";
        try {
            return qr.update(connection, sql,aState,aid);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public int updateAddress(Address address) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "update address set a_name=?,a_phone=?,a_detail=?,a_state=? where a_id=?";
        Object[] params = {address.getaName(),address.getaPhone(),address.getaDetail(),address.getaState(),address.getaId()};
        try {
            return qr.update(connection, sql,params);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }
}
