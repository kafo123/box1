package com.qf.controller;

import com.google.gson.Gson;
import com.qf.annotation.ContentType;
import com.qf.util.SysContant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author kafo
 * @description
 * @date 2020/9/15
 * @TIME 11:45
 */
public class BaseController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if(method==null){
            resp.sendRedirect(req.getContextPath()+"/");
            return;
        }
        try{
            Class aClass = this.getClass();
            Object o = aClass.newInstance();
            Method method1 = aClass.getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
            Object result = method1.invoke(o, req, resp);
            if(method1.isAnnotationPresent(ContentType.class)){
                ContentType annotation = method1.getAnnotation(ContentType.class);
                String type = annotation.value();
                resp.setContentType(type);
            }else{
                resp.setContentType("text/html;charset=utf-8");
            }
            if(result!=null){
                if(result.getClass().getSimpleName().equalsIgnoreCase("String")) {
                    String str = (String) result;
                    String suffix = str.substring(str.indexOf(SysContant.FLAG) + 1);
                    if (str.startsWith(SysContant.FORWORD)){
                        req.getRequestDispatcher("/"+suffix).forward(req, resp);
                        return;
                    }
                    if(str.startsWith(SysContant.REDIRECT)){
                        resp.sendRedirect(req.getContextPath()+"/"+suffix);
                        return;
                    }
                }else{//处理json
                    Gson gson = new Gson();
                    String s = gson.toJson(result);
                    resp.getWriter().print(s);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
