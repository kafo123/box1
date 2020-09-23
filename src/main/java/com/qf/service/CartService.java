package com.qf.service;

import com.qf.entity.Cart;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/16
 * @TIME 14:00
 */
public interface CartService {

    int addGoodToCart(Cart cart) throws SQLException;
    List<Cart> getAllCart(int uid) throws SQLException, InvocationTargetException, IllegalAccessException;
    int updateNum(int uid, int pid, BigDecimal price) throws SQLException;
    int deleteCart(int uid, int pid)throws SQLException;
    int deleteAllCart(int uid)throws SQLException;
    int updateAddNum(int uid, int pid, BigDecimal price)throws SQLException;
}
