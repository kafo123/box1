package com.qf.dao.impl;

import com.qf.dao.CartDao;
import com.qf.entity.Cart;
import com.qf.entity.Product;
import com.qf.util.DruidUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
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
 * @date 2020/9/16
 * @TIME 13:50
 */
public class CartDaoImpl implements CartDao {
    QueryRunner qr = new QueryRunner();
    @Override
    public Cart findCarByUidAndPid(int uid, int pid) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "select  c_id as cId,u_id as uId,p_id as pId,c_count as cCount,c_num as cNum from cart where u_id=? and p_id=?";
        try {
            return qr.query(connection, sql, new BeanHandler<>(Cart.class), uid,pid);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public int insertCart(Cart cart) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "insert into cart(u_id,p_id,c_count,c_num) value(?,?,?,?)";
        try {
            return qr.update(connection, sql, cart.getuId(),cart.getpId(),cart.getcCount(),cart.getcNum());
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public int updateCartIfExist(Cart cart) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "update cart set c_num=?,c_count=? where c_id=?";
        try {
            return qr.update(connection, sql,cart.getcNum(),cart.getcCount(),cart.getcId());
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public List<Cart> findAllCart(int uId) throws SQLException, InvocationTargetException, IllegalAccessException {
        String sql = "select c_id as cId,u_id as uId,c.p_id as pId,c_count as cCount,c_num as cNum,p_name as pName,p_time as pTime,p_image as pImage,p_price as pPrice,p_state as pState,p_info as pInfo from cart c inner join product p on c.p_id=p.p_id where c.u_id=?";
        Connection connection = DruidUtils.getConnection();
        List<Map<String, Object>> query = qr.query(connection, sql, new MapListHandler(), uId);
        List<Cart> carts = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : query) {
            Cart cart = new Cart();
            Product product = new Product();
            BeanUtils.populate(cart, stringObjectMap);
            BeanUtils.populate(product, stringObjectMap);
            cart.setProduct(product);
            carts.add(cart);
        }
        try {
            return carts;
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public int deleteCart(int uid, int pid) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "delete from cart where u_id = ? and p_id=?";
        try {
            return qr.update(connection, sql,uid,pid);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public int deleteAllCart(int uid) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "delete from cart where u_id = ? ";
        try {
            return qr.update(connection, sql,uid);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }
//    public static void main(String[] args) throws IllegalAccessException, SQLException, InvocationTargetException {
//        CartDao cartDao = new CartDaoImpl();
//        List<Cart> allCart = cartDao.findAllCart(8);
//        System.out.println(allCart);
//    }
}
