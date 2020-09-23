package com.qf.service;

import com.qf.entity.Order;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 12:15
 */
public interface OrderService {

    int createOrder(Order order) throws SQLException, InvocationTargetException, IllegalAccessException;

    List<Order> getAllOrder(int uid) throws Exception;

    Order getOrderDetail(String oid) throws IllegalAccessException, SQLException, InvocationTargetException;

    int updateState(Order order) throws SQLException;

    List<Order> getAllOrder() throws SQLException;

    List<Order> getOrdersByUnameAndOstate(String uName, int oState) throws SQLException;
}
