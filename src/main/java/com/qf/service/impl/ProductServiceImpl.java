package com.qf.service.impl;

import com.qf.dao.ProductDao;
import com.qf.dao.impl.ProductDaoImpl;
import com.qf.entity.Product;
import com.qf.service.ProductService;
import com.qf.util.PageBean;
import com.qf.util.StringUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/15
 * @TIME 17:53
 */
public class ProductServiceImpl implements ProductService {
    private ProductDao productDao = new ProductDaoImpl();
    @Override
    public PageBean<Product> getPageBeanByTypeId(int pageIndex, int pageSize, int typeId) throws SQLException {
        long count = productDao.countProduct(typeId);
        int startIndex = (pageIndex-1)*pageSize;
        List<Product> pageData = productDao.findPageProductByTypeId(startIndex, pageSize, typeId);
        PageBean<Product> pageBean = new PageBean<>(pageIndex, pageSize, count, pageData);
        pageBean.setStartAndEnd();
        return pageBean;
    }

    @Override
    public Product findProductDetailById(int id) throws SQLException {
        return productDao.findProductById(id);
    }

    @Override
    public PageBean<Product> getPageBeanByProductName(int pageIndex, int pageSize, String productName) throws SQLException {
        long count = productDao.countProduct(productName);
        int startIndex = (pageIndex-1)*pageSize;
        List<Product> pageData = productDao.findPageProductByName(startIndex, pageSize, productName);
        PageBean<Product> pageBean = new PageBean<>(pageIndex, pageSize, count, pageData);
        pageBean.setStartAndEnd();
        return pageBean;
    }

    @Override
    public List<Product> getAllProduct() throws SQLException {
        return productDao.findAllProdect();
    }

    @Override
    public int addProduct(Product product) throws SQLException {
        return productDao.insertProduct(product);
    }

    @Override
    public List<Product> getProductsBypNameAndpTime(String pName, String pTime) throws SQLException {
        StringBuilder sql = new StringBuilder("select p_id as pId,p_name as pName,p_time as pTime,p_price as pPrice, p_info as pInfo,t.t_name as tName from product p inner join type t on p.t_id=t.t_id where 1=1");
        if(!StringUtil.isEmpty(pName)){
            sql.append("  and p_name like '%"+pName+"%'");
        }
        if(!StringUtil.isEmpty(pTime)){
            sql.append("   and p_time='"+pTime+"'");
        }
        return productDao.findProductsByProductNameAndProductTime(sql.toString());
    }
}
