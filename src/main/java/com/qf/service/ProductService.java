package com.qf.service;

import com.qf.entity.Product;
import com.qf.util.PageBean;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/15
 * @TIME 17:51
 */
public interface ProductService {
    PageBean<Product> getPageBeanByTypeId(int pageIndex, int pageSize, int typeId) throws SQLException;
    Product findProductDetailById(int id)throws SQLException;
    PageBean<Product> getPageBeanByProductName(int pageIndex, int pageSize, String productName) throws SQLException;
    List<Product> getAllProduct() throws SQLException;
    int addProduct(Product product) throws SQLException;
    List<Product> getProductsBypNameAndpTime(String pName, String pTime) throws SQLException;
}
