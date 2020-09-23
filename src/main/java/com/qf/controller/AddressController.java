package com.qf.controller;

import com.qf.annotation.ContentType;
import com.qf.entity.Address;
import com.qf.entity.User;
import com.qf.service.AddressService;
import com.qf.service.impl.AddressServiceImpl;
import com.qf.util.SysContant;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 19:06
 */
@WebServlet("/addressController")
public class AddressController extends BaseController {

    public String addressManage(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return SysContant.REDIRECT + SysContant.FLAG + "login.jsp";
        }
        int uId = user.getuId();
        AddressService addressService = new AddressServiceImpl();
        List<Address> allAddress = addressService.getAllAddress(uId);
        request.setAttribute("addressList", allAddress);
        return SysContant.FORWORD+SysContant.FLAG+"self_info.jsp";
    }

    public String addAddress(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, SQLException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return SysContant.REDIRECT + SysContant.FLAG + "login.jsp";
        }
        int uId = user.getuId();
        Address address = new Address();
        BeanUtils.populate(address, request.getParameterMap());
        address.setuId(uId);
        AddressService addressService = new AddressServiceImpl();
        int i = addressService.addAddress(address);
        if(i>0){
            return SysContant.REDIRECT+SysContant.FLAG+"addressController?method=addressManage";
        }else{
            request.setAttribute("msg", "添加收货地址失败");
            return SysContant.FORWORD+SysContant.FLAG+"message.jsp";
        }
    }

    @ContentType("application/json;charset=utf-8")
    public Map<String,Object> deleteAddress(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User) request.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        if (user == null) {
            map.put("code", -1);
            return map;
        }
        int aid = Integer.parseInt(request.getParameter("id"));
        AddressService addressService = new AddressServiceImpl();
        int i = addressService.removeAddress(aid);
        if(i>0){
            map.put("code", 1);
        }else {
            map.put("code", 0);
        }
        return map;
    }
    @ContentType("application/json;charset=utf-8")
    public Map<String, Object> setDefaultAddress(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User) request.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<>();
        if (user == null) {
            map.put("code", -1);
            return map;
        }
        int uId = user.getuId();
        int aid = Integer.parseInt(request.getParameter("id"));
        AddressService addressService = new AddressServiceImpl();
        int i = addressService.updateAddressStateByAidAndUid(aid, uId);
        if(i>0){
            map.put("code", 1);
        }else {
            map.put("code", 0);
        }
        return map;
    }

    public String updateAddress(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, SQLException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return SysContant.REDIRECT + SysContant.FLAG + "login.jsp";
        }
        Address address = new Address();
        BeanUtils.populate(address, request.getParameterMap());
        AddressService addressService = new AddressServiceImpl();
        int i = addressService.updateAddress(address);
        if(i>0){
            return SysContant.REDIRECT+SysContant.FLAG+"addressController?method=addressManage";
        }else {
            request.setAttribute("msg", "更新地址失败");
            return SysContant.FORWORD+SysContant.FLAG+"message.jsp";
        }
    }

}
