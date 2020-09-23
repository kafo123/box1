package com.qf.dao;

import com.qf.entity.Order;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 12:09
 */
public interface OrderDao {

    /**
     * 插入订单
     * @param order
     * @return
     * @throws SQLException
     */
    int insertOrder(Order order) throws SQLException;

    /**
     * 通过用户id查询所有订单
     * @param uid 用户id
     * @return
     * @throws SQLException
     * @throws Exception
     */
    List<Order> findAllOrder(int uid) throws SQLException, Exception;

    /**
     * 查询订单的相关信息
     * @param oid 订单号
     * @return
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    Order findOrderDetailByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException;

    /**
     * 更订单的状态
     * @param order 提供订单id和订单状态
     * @return
     * @throws SQLException
     */
    int updateState(Order order) throws SQLException;

    /**
     * c查询所有的订单
     * @return
     * @throws SQLException
     */
    List<Order> findAllOrder() throws SQLException;

    /**
     * 通过用户名或订单状态查询订单
     * @param sql 动态sql
     * @return
     * @throws SQLException
     */
    List<Order> findOrdersByUnameAndOState(String sql) throws SQLException;
}
