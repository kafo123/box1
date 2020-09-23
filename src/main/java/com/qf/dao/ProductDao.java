package com.qf.dao;

import com.qf.entity.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/15
 * @TIME 17:11
 */
public interface ProductDao {

    /**
     * 通过类型id查询产品的数量
     *
     * @param typeId 产品类型id
     * @return
     * @throws SQLException
     */
    long countProduct(int typeId) throws SQLException;

    /**
     * 分页查询属于某类型的产品
     *
     * @param startIndex 起始索引
     * @param pageSize   页大小
     * @param typeId     类型id
     * @return 产品集合
     * @throws SQLException
     */
    List<Product> findPageProductByTypeId(int startIndex, int pageSize, int typeId) throws SQLException;

    /**
     * 通过产品id查询产品信息和所属类型名
     *
     * @param id 产品id
     * @return 产品实体
     * @throws SQLException
     */
    Product findProductById(int id) throws SQLException;

    /**
     * 通过产品名分页查询产品
     *
     * @param startIndex  起始索引
     * @param pageSize    页大小
     * @param productName 产品名
     * @return 产品列表
     * @throws SQLException
     */
    List<Product> findPageProductByName(int startIndex, int pageSize, String productName) throws SQLException;

    /**
     * 模糊查询产品名的数量
     *
     * @param productName
     * @return
     * @throws SQLException
     */
    long countProduct(String productName) throws SQLException;

    //---------------------------管理员模块--------------------

    /**
     * 查询所有的产品，包括产品类别名
     *
     * @return
     * @throws SQLException
     */
    List<Product> findAllProdect() throws SQLException;

    /**
     * 插入产品
     *
     * @param product
     * @return
     * @throws SQLException
     */
    int insertProduct(Product product) throws SQLException;

    /**
     * 通过产品名和时间动态查询产品
     *
     * @param sql 动态sql
     * @return
     * @throws SQLException
     */
    List<Product> findProductsByProductNameAndProductTime(String sql) throws SQLException;
}
