package com.qf.dao.impl;

import com.qf.dao.OrderDao;
import com.qf.entity.Address;
import com.qf.entity.Item;
import com.qf.entity.Order;
import com.qf.entity.Product;
import com.qf.util.DruidUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 12:10
 */
public class OrderDaoImpl implements OrderDao {
    QueryRunner qr = new QueryRunner();
    @Override
    public int insertOrder(Order order) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "insert into orders(o_id,u_id,a_id,o_count,o_time,o_state) value(?,?,?,?,?,?)";
        Object[] params = {order.getoId(),order.getuId(),order.getaId(),order.getoCount(),order.getoTime(),order.getoState()};
        try {
            return qr.update(connection, sql,params);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public List<Order> findAllOrder(int uid) throws Exception {
        Connection connection = DruidUtils.getConnection();
        String sql = "select o_id as oId,o.u_id as uId,o_count as oCount,o_time as oTime,o_state as oState,a_detail as aDetail from orders o inner join address a on o.a_id=a.a_id where o.u_id=?";
        try {
            List<Map<String, Object>> query = qr.query(connection, sql, new MapListHandler(), uid);
            List<Order> list = new ArrayList<>();
            for (Map<String, Object> map : query) {
                Order order = new Order();
                Address address = new Address();
                BeanUtils.populate(order, map);
                BeanUtils.populate(address,map);
                order.setAddress(address);
                list.add(order);
            }
            return list;
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public Order findOrderDetailByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException {
        Connection connection = DruidUtils.getConnection();
        String sql = "select o.o_id as oId,o_time as oTime,o_count as oCount,a_name as aName,a_phone as aPhone,a_detail as aDetail,i_count as iCount,i_num as iNum,p_name as pName,p_time as pTime,p_image as pImage,p_price as pPrice,p_state as pState  from orders o inner join address a on o.a_id=a.a_id inner join item i on o.o_id = i.o_id inner join product p on i.p_id = p.p_id where o.o_id=?";
        List<Item> itemList = new ArrayList<>();
        try{
        List<Map<String, Object>> map = qr.query(connection, sql, new MapListHandler(), oid);
        Order order = new Order();
        for (Map<String, Object> query : map) {
            Address address = new Address();
            Item item = new Item();
            Product product = new Product();
            BeanUtils.populate(order, query);
            BeanUtils.populate(address, query);
            BeanUtils.populate(item, query);
            BeanUtils.populate(product, query);
            order.setAddress(address);
            item.setProduct(product);
            itemList.add(item);
        }
        order.setItemList(itemList);
        return order;
        }finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public int updateState(Order order) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "update orders set o_state=? where o_id=?";
        try {
            return qr.update(connection, sql,order.getoState(),order.getoId());
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public List<Order> findAllOrder() throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "select o_id as oId,o.u_id as uId,o_count as oCount,o_time as oTime,o_state as oState,u_name as uName from orders o inner join user u on o.u_id=u.u_id";
        try {
            return qr.query(connection, sql, new BeanListHandler<>(Order.class));
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public List<Order> findOrdersByUnameAndOState(String sql) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        try {
            return qr.query(connection, sql, new BeanListHandler<>(Order.class));
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }
}
