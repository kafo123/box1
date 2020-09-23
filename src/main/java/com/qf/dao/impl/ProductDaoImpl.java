package com.qf.dao.impl;

import com.qf.dao.ProductDao;
import com.qf.entity.Product;
import com.qf.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/15
 * @TIME 17:21
 */
public class ProductDaoImpl implements ProductDao {
    QueryRunner qr = new QueryRunner();

    @Override
    public long countProduct(int typeId) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "select count(*) from product where t_id=?";
        try {
            long count = (Long) qr.query(connection, sql, new ScalarHandler(), typeId);
            return count;
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public List<Product> findPageProductByTypeId(int startIndex, int pageSize, int typeId) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "select p_id as pId,p_name as pName,p_time as pTime," +
                "p_image as pImage, p_price as pPrice,p_state as pState," +
                "p_info as pInfo from product where t_id=? limit ?,?";
        try {
            List<Product> pageData = qr.query(connection, sql, new BeanListHandler<>(Product.class), typeId, startIndex, pageSize);
            return pageData;
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public Product findProductById(int id) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "select p_id as pId,p_name as pName,p_time as pTime," +
                "p_image as pImage, p_price as pPrice,p_state as pState," +
                "p_info as pInfo,p.t_id as tId,t.t_name as tName from product p inner join type t on p.t_id=t.t_id where p.p_id=?";
        try {
            return qr.query(connection, sql, new BeanHandler<>(Product.class), id);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public List<Product> findPageProductByName(int startIndex, int pageSize, String productName) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "select p_id as pId,p_name as pName,p_time as pTime," +
                "p_image as pImage, p_price as pPrice,p_state as pState," +
                "p_info as pInfo from product where p_name like ? limit ?,?";
        Object[] params = {"%"+productName+"%",startIndex,pageSize};
        try {
            return qr.query(connection, sql, new BeanListHandler<>(Product.class),params);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public long countProduct(String productName) throws SQLException {
        String sql = "select count(*) from product where p_name like ?";
        Connection connection = DruidUtils.getConnection();
        try {
            return (long)qr.query(connection, sql, new ScalarHandler(), "%"+productName+"%");
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public List<Product> findAllProdect() throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "select p_id as pId,p_name as pName,p_time as pTime," +
                " p_price as pPrice," +
                "p_info as pInfo,t.t_name as tName from product p inner join type t on p.t_id=t.t_id";
        try {
            return qr.query(connection, sql, new BeanListHandler<>(Product.class));
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }

    @Override
    public int insertProduct(Product product) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "insert into product(t_id,p_name,p_time,p_image,p_price,p_state,p_info) value(?,?,?,?,?,?,?)";
        Object[] params = {product.gettId(),product.getpName(),product.getpTime(),product.getpImage(),product.getpPrice(),product.getpState(),product.getpInfo()};
        try {
            return qr.update(connection, sql,params);
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }
//-----------------------------------管理员模块-----------------------
    @Override
    public List<Product> findProductsByProductNameAndProductTime(String sql) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        try {
            return qr.query(connection,sql, new BeanListHandler<>(Product.class));
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }
}
