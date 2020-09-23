package com.qf.service.impl;

import com.qf.dao.CartDao;
import com.qf.dao.impl.CartDaoImpl;
import com.qf.entity.Cart;
import com.qf.service.CartService;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/16
 * @TIME 14:02
 */
public class CartServiceImpl implements CartService {
    CartDao cartDao = new CartDaoImpl();
    @Override
    public int addGoodToCart(Cart cart) throws SQLException {
        Cart cart1 = cartDao.findCarByUidAndPid(cart.getuId(), cart.getpId());
        int i;
        if(cart1==null){
            i = cartDao.insertCart(cart);
        }else {
            cart1.setcCount(cart.getcCount().add(cart1.getcCount()));
            cart1.setcNum(cart1.getcNum()+1);
           i = cartDao.updateCartIfExist(cart1);
        }
        return i;
    }

    @Override
    public List<Cart> getAllCart(int uid) throws SQLException, InvocationTargetException, IllegalAccessException {
        return cartDao.findAllCart(uid);
    }

    @Override
    public int updateNum(int uid, int pid, BigDecimal price) throws SQLException {
        Cart cart = cartDao.findCarByUidAndPid(uid, pid);
        cart.setcNum(cart.getcNum()-1);
        cart.setcCount(cart.getcCount().subtract(price));
        int i = cartDao.updateCartIfExist(cart);
        return i;
    }

    @Override
    public int deleteCart(int uid, int pid) throws SQLException {
        return cartDao.deleteCart(uid, pid);
    }

    @Override
    public int deleteAllCart(int uid) throws SQLException {
        return cartDao.deleteAllCart(uid);
    }

    @Override
    public int updateAddNum(int uid, int pid,BigDecimal price) throws SQLException {
        Cart cart = cartDao.findCarByUidAndPid(uid, pid);
        cart.setcNum(cart.getcNum()+1);
        cart.setcCount(cart.getcCount().add(price));
        int i = cartDao.updateCartIfExist(cart);
        return i;
    }

}
