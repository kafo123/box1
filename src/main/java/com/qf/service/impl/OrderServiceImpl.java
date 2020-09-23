package com.qf.service.impl;

import com.qf.dao.CartDao;
import com.qf.dao.ItemDao;
import com.qf.dao.OrderDao;
import com.qf.dao.impl.CartDaoImpl;
import com.qf.dao.impl.ItemDaoImpl;
import com.qf.dao.impl.OrderDaoImpl;
import com.qf.entity.Cart;
import com.qf.entity.Item;
import com.qf.entity.Order;
import com.qf.service.OrderService;
import com.qf.util.DruidUtils;
import com.qf.util.OrderIdUtil;
import com.qf.util.StringUtil;
import com.qf.util.SysContant;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 12:16
 */
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private CartDao cartDao = new CartDaoImpl();
    private ItemDao itemDao = new ItemDaoImpl();
    @Override
    public int createOrder(Order order) throws SQLException, InvocationTargetException, IllegalAccessException {
        order.setoId(OrderIdUtil.createOrderId(order.getuId()));
        order.setoTime(new Date());
        order.setoState(SysContant.OrderState.NOT_PAY.getValue());
        int i = 0;
        try {
            DruidUtils.beginTransaction();
            List<Cart> allCart = cartDao.findAllCart(order.getuId());
            i = orderDao.insertOrder(order);
            for (Cart cart : allCart) {
                Item item = new Item();
                item.setoId(order.getoId());
                item.setpId(cart.getpId());
                item.setiCount(cart.getcCount());
                item.setiNum(cart.getcNum());
                itemDao.insertItem(item);
            }
            i += cartDao.deleteAllCart(order.getuId());
            DruidUtils.commit();
        } catch (Exception e) {
            e.printStackTrace();
            DruidUtils.rollback();
        }finally {
            DruidUtils.close();
        }
        return i;
    }

    @Override
    public List<Order> getAllOrder(int uid) throws Exception {
        return orderDao.findAllOrder(uid);
    }

    @Override
    public Order getOrderDetail(String oid) throws IllegalAccessException, SQLException, InvocationTargetException {
        return orderDao.findOrderDetailByOid(oid);
    }

    @Override
    public int updateState(Order order) throws SQLException {
        return orderDao.updateState(order);
    }
    //--------------------------------管理员模块------------------
    @Override
    public List<Order> getAllOrder() throws SQLException {
        return orderDao.findAllOrder();
    }

    @Override
    public List<Order> getOrdersByUnameAndOstate(String uName, int oState) throws SQLException {
        StringBuilder sql = new StringBuilder("select o_id as oId,o_count as oCount,o_time as oTime,o_state as oState,u_name as uName from orders o inner join user u on o.u_id=u.u_id where 1=1");
        if(!StringUtil.isEmpty(uName)){
            sql.append("   and u_name like '%"+uName+"%'");
        }
        if(oState!=-1){
            sql.append(" and o_state="+oState);
        }
        return orderDao.findOrdersByUnameAndOState(sql.toString());
    }
}
