package com.qf.dao;

import com.qf.entity.Cart;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/16
 * @TIME 13:47
 */
public interface CartDao {

    Cart findCarByUidAndPid(int uid, int pid) throws SQLException;
    int insertCart(Cart cart) throws SQLException;
    int updateCartIfExist(Cart cart) throws SQLException;
    List<Cart> findAllCart(int uId) throws SQLException, InvocationTargetException, IllegalAccessException;
    int deleteCart(int uid, int pid)throws SQLException;
    int deleteAllCart(int uid)throws SQLException;
}
