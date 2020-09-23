package com.qf.controller;

import com.qf.entity.Address;
import com.qf.entity.Cart;
import com.qf.entity.Order;
import com.qf.entity.User;
import com.qf.service.AddressService;
import com.qf.service.CartService;
import com.qf.service.OrderService;
import com.qf.service.impl.AddressServiceImpl;
import com.qf.service.impl.CartServiceImpl;
import com.qf.service.impl.OrderServiceImpl;
import com.qf.util.SysContant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 9:57
 */
@WebServlet("/orderController")
public class OrderController extends BaseController {

    public String getOrderView(HttpServletRequest request, HttpServletResponse response) throws SQLException, InvocationTargetException, IllegalAccessException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return SysContant.REDIRECT + SysContant.FLAG + "login.jsp";
        }
        int uId = user.getuId();
        AddressService addressService = new AddressServiceImpl();
        CartService cartService = new CartServiceImpl();
        List<Cart> allCart = cartService.getAllCart(uId);
        List<Address> addressList = addressService.getAllAddress(uId);
        request.setAttribute("carts", allCart);
        request.setAttribute("addresses", addressList);
        return SysContant.FORWORD+SysContant.FLAG+"order.jsp";
    }

    public String addOrder(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, SQLException, InvocationTargetException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return SysContant.REDIRECT + SysContant.FLAG + "login.jsp";
        }
        int uId = user.getuId();
        int aId = Integer.parseInt( request.getParameter("aid"));
        BigDecimal sum = new BigDecimal(request.getParameter("sum"));
        OrderService orderService = new OrderServiceImpl();
        Order order = new Order();
        order.setuId(uId);
        order.setaId(aId);
        order.setoCount(sum);
        int order1 = orderService.createOrder(order);
        if(order1>1){
            return SysContant.FORWORD+SysContant.FLAG+"orderController?method=getOrderList";
        }else {
            request.setAttribute("msg", "创建订单失败");
            return SysContant.FORWORD+SysContant.FLAG+"message.jsp";
        }
    }

    public String getOrderList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return SysContant.REDIRECT + SysContant.FLAG + "login.jsp";
        }
        int uId = user.getuId();
        OrderService orderService = new OrderServiceImpl();
        List<Order> allOrder = orderService.getAllOrder(uId);
        request.setAttribute("orderList", allOrder);
        return SysContant.FORWORD+SysContant.FLAG+"orderList.jsp";
    }

    public String getOrderDetail(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, SQLException, InvocationTargetException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return SysContant.REDIRECT + SysContant.FLAG + "login.jsp";
        }
        String oid = request.getParameter("oid");
        OrderService orderService = new OrderServiceImpl();
        Order orderDetail = orderService.getOrderDetail(oid);
        request.setAttribute("od", orderDetail);
        return SysContant.FORWORD+SysContant.FLAG+"orderDetail.jsp";
    }

    public String updateState(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String oid = request.getParameter("oid");
        Order order = new Order();
        order.setoId(oid);
        order.setoState(1);
        OrderService orderService = new OrderServiceImpl();
        int i = orderService.updateState(order);
        if(i>0){
            request.setAttribute("msg","支付成功");
            return SysContant.FORWORD+SysContant.FLAG+"message.jsp";
        }else{
            request.setAttribute("msg","订单异常");
            return SysContant.FORWORD+SysContant.FLAG+"message.jsp";
        }
    }

    //---------------------------------管理员模块--------------------------------
    public String getAllOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        OrderService orderService = new OrderServiceImpl();
        List<Order> allOrder = orderService.getAllOrder();
        request.setAttribute("orderList", allOrder);
        return SysContant.FORWORD+SysContant.FLAG+"admin/showAllOrder.jsp";
    }

    public String sendOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String oid = request.getParameter("oid");
        Order order = new Order();
        order.setoId(oid);
        order.setoState(SysContant.OrderState.SENDED_NOT_RECEIVE.getValue());
        OrderService orderService = new OrderServiceImpl();
        int i = orderService.updateState(order);
        if(i>0){
            return SysContant.FORWORD+SysContant.FLAG+"orderController?method=getAllOrder";
        }else{
            request.setAttribute("msg","更新失败");
            return SysContant.FORWORD+SysContant.FLAG+"showAllOrder.jsp";
        }
    }

    /**
     * 通过姓名
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String searchOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        int oState = Integer.parseInt(request.getParameter("status"));
        OrderService orderService = new OrderServiceImpl();
        List<Order> allOrder = orderService.getOrdersByUnameAndOstate(username, oState);
        request.setAttribute("orderList", allOrder);
        return SysContant.FORWORD+SysContant.FLAG+"admin/showAllOrder.jsp";
    }
}
