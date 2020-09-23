package com.qf.controller;

import com.qf.annotation.ContentType;
import com.qf.entity.Product;
import com.qf.entity.Type;
import com.qf.service.GoodService;
import com.qf.service.ProductService;
import com.qf.service.impl.GoodServiceImpl;
import com.qf.service.impl.ProductServiceImpl;
import com.qf.util.PageBean;
import com.qf.util.StringUtil;
import com.qf.util.SysContant;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/15
 * @TIME 15:59
 */
@WebServlet("/goodsController")
@MultipartConfig(maxRequestSize = 1024*1024*20,maxFileSize = 1024*1024*5)
public class GoodsController extends BaseController {
    @ContentType("application/json;charset=utf-8")
    public List<Type> getAllType(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        GoodService goodService = new GoodServiceImpl();
        List<Type> allType = goodService.getAllType();
        return allType;
    }

    public String getPageData(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String typeid = request.getParameter("typeid");
        String pageSize = request.getParameter("pageSize");
        String pageIndex = request.getParameter("pageIndex");
        int pageSize1;
        int pageIndex1;
        if(pageSize==null){
            pageSize1=5;
        }else{
            pageSize1=Integer.parseInt(pageSize);
        }
        if(pageIndex==null){
            pageIndex1=1;
        }else{
            pageIndex1=Integer.parseInt(pageIndex);
        }
        ProductService productService = new ProductServiceImpl();
        PageBean<Product> pageBean = productService.getPageBeanByTypeId(pageIndex1, pageSize1, Integer.parseInt(typeid));
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("typeid", typeid);
        return SysContant.FORWORD+SysContant.FLAG+"goodsList.jsp";
    }

    public String getPageDataByName(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String typeid = request.getParameter("typeid");
        String productName= request.getParameter("pName");
        String pageSize = request.getParameter("pageSize");
        String pageIndex = request.getParameter("pageIndex");
        int pageSize1;
        int pageIndex1;
        if(pageSize==null){
            pageSize1=5;
        }else{
            pageSize1=Integer.parseInt(pageSize);
        }
        if(pageIndex==null){
            pageIndex1=1;
        }else{
            pageIndex1=Integer.parseInt(pageIndex);
        }
        ProductService productService = new ProductServiceImpl();
        PageBean<Product> pageBean = productService.getPageBeanByProductName(pageIndex1, pageSize1, productName);
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("typeid", typeid);
        request.setAttribute("pName", productName);
        return SysContant.FORWORD+SysContant.FLAG+"goodsList.jsp";
    }

    public String  getProductDetail(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        ProductService productService = new ProductServiceImpl();
        Product product = productService.findProductDetailById(id);
        request.setAttribute("goods", product);
        return SysContant.FORWORD+SysContant.FLAG+"goodsDetail.jsp";

    }

    //-------------------------管理员模块---------------------------------
    public String adminGetAllType(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        GoodService goodService = new GoodServiceImpl();
        List<Type> allType = goodService.getAllType();
        request.setAttribute("goodsTypeList", allType);
        return SysContant.FORWORD+SysContant.FLAG+"admin/showGoodsType.jsp";
    }

    public String adminAddType(HttpServletRequest request, HttpServletResponse response) throws SQLException{
        String typename = request.getParameter("typename");
        String typeinfo = request.getParameter("typeinfo");
        Type type = new Type(0, typename, typeinfo);
        GoodService goodService = new GoodServiceImpl();
        int i = goodService.addType(type);
        if(i>0){
            return SysContant.REDIRECT+SysContant.FLAG+"goodsController?method=adminGetAllType";
        }else {
            request.setAttribute("msg", "添加商品类型失败");
            return SysContant.FORWORD+SysContant.FLAG+"admin/addGoodsType.jsp";
        }

    }

    public String getAllProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        ProductService productService = new ProductServiceImpl();
        List<Product> allProduct = productService.getAllProduct();
        request.setAttribute("goodsList", allProduct);
        return SysContant.FORWORD+SysContant.FLAG+"admin/showGoods.jsp";
    }

    public String addProduct(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException, ServletException, SQLException {
        Product product = new Product();
        try {
            ConvertUtils.register(new Converter() {
                @Override
                public Object convert(Class aClass, Object o) {
                    String str = (String)o;
                    if(StringUtil.isEmpty(str)){
                        return null;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date parse=null;
                    try {
                         parse = sdf.parse(str);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return parse;
                }
            }, Date.class);

            BeanUtils.populate(product, request.getParameterMap());
            Part imagePart = request.getPart("pImage");
            if(!StringUtil.isEmpty(imagePart.getSubmittedFileName())){
                String realPath = request.getServletContext().getRealPath("/imgage");
                File file = new File(realPath);
                if(!file.exists()){
                    file.mkdirs();
                }
                String header = imagePart.getHeader("content-disposition");
                String filename = header.substring(header.lastIndexOf("filename=") + 10,header.length()-1);
                filename = filename.substring(filename.lastIndexOf("\\")+1);
                product.setpImage("image"+File.separator+filename);
                imagePart.write(realPath+File.separator+filename);
                imagePart.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ProductService productService = new ProductServiceImpl();
        int i = productService.addProduct(product);
        if(i>0){
           return SysContant.REDIRECT+SysContant.FLAG+"goodsController?method=getAllProduct" ;
        }else {
            request.setAttribute("msg", "添加商品失败");
            return SysContant.FORWORD+SysContant.FLAG+"admin/addGoods.jsp";
        }
    }

    public String searchGoods(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String name = request.getParameter("name");
        String time = request.getParameter("time");
        ProductService productService = new ProductServiceImpl();
        List<Product> allProduct = productService.getProductsBypNameAndpTime(name, time);
        request.setAttribute("goodsList", allProduct);
        return SysContant.FORWORD+SysContant.FLAG+"admin/showGoods.jsp";
    }
}
