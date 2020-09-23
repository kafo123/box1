package com.qf.controller;

import com.qf.annotation.ContentType;
import com.qf.entity.Cart;
import com.qf.entity.User;
import com.qf.service.CartService;
import com.qf.service.impl.CartServiceImpl;
import com.qf.util.SysContant;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kafo
 * @description
 * @date 2020/9/16
 * @TIME 14:57
 */
@WebServlet("/cartController")
public class CartController extends BaseController {

    public String addGoodsToCart(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int pId = Integer.parseInt(request.getParameter("goodsId"));
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return SysContant.REDIRECT + SysContant.FLAG + "login.jsp";
        }
        int uId = user.getuId();
        Cart cart = new Cart(0, uId, pId, price, 1);
        CartService cartService = new CartServiceImpl();
        int i = cartService.addGoodToCart(cart);
        if(i>0){
            return SysContant.REDIRECT+SysContant.FLAG+"cartSuccess.jsp";
        }
        return SysContant.FORWORD+SysContant.FLAG+"message.jsp";
    }

    public String getCart(HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, SQLException, InvocationTargetException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return SysContant.REDIRECT + SysContant.FLAG + "login.jsp";
        }
        int uId = user.getuId();
        CartService cartService = new CartServiceImpl();
        List<Cart> allCart = cartService.getAllCart(uId);
        request.setAttribute("carts", allCart);
        return SysContant.FORWORD+SysContant.FLAG+"cart.jsp";
    }
    @ContentType("application/json;charset=utf-8")
    public Map<String,Object> updateNum(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        int pid = Integer.parseInt(request.getParameter("pid"));
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        CartService cartService = new CartServiceImpl();
        User user = (User) request.getSession().getAttribute("user");
        Map<String,Object> map = new HashMap<>();
        if (user == null) {
            map.put("code", -1);
            return map ;
        }
        int uId = user.getuId();
        int i = cartService.updateNum(uId, pid, price);
        if(i>0){
            map.put("code", 1);
            return map;
        }else {
            map.put("code", 0);
            return map;
        }
    }

    @ContentType("application/json;charset=utf-8")
    public Map<String,Object> deleteCart(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int pId = Integer.parseInt(request.getParameter("pid"));
        User user = (User) request.getSession().getAttribute("user");
        Map<String,Object> map = new HashMap<>();
        if (user == null) {
            map.put("code", -1);
            return map ;
        }
        int uId = user.getuId();
        CartService cartService = new CartServiceImpl();
        int i = cartService.deleteCart(uId, pId);
        if(i>0){
            map.put("code", 1);
        }else {
            map.put("code", 0);
        }
        return map;
    }

    public String deleteAllCart(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return SysContant.REDIRECT + SysContant.FLAG + "login.jsp";
        }
        int uId = user.getuId();
        CartService cartService = new CartServiceImpl();
        int i = cartService.deleteAllCart(uId);
        if(i>0){
            return SysContant.REDIRECT+SysContant.FLAG+"cartController?method=getCart";
        }
        request.setAttribute("msg", "删除购物车全部商品失败");
        return SysContant.FORWORD+SysContant.FLAG+"message.jsp";
    }
    @ContentType("application/json;charset=utf-8")
    public Map<String,Object> updateAddNum(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int pid = Integer.parseInt(request.getParameter("pid"));
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        CartService cartService = new CartServiceImpl();
        User user = (User) request.getSession().getAttribute("user");
        Map<String,Object> map = new HashMap<>();
        if (user == null) {
            map.put("code", -1);
            return map ;
        }
        int uId = user.getuId();
        int i = cartService.updateAddNum(uId, pid, price);
        if(i>0){
            map.put("code", 1);
        }else {
            map.put("code", 0);
        }
        return map;
    }
}
