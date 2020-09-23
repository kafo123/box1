package com.qf.dao.impl;

import com.qf.dao.ItemDao;
import com.qf.entity.Item;
import com.qf.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 13:53
 */
public class ItemDaoImpl implements ItemDao {
    private QueryRunner qr = new QueryRunner();
    @Override
    public int insertItem(Item item) throws SQLException {
        Connection connection = DruidUtils.getConnection();
        String sql = "insert into item(o_id,p_id,i_count,i_num) value(?,?,?,?)";
            try {
            return qr.update(connection, sql,item.getoId(),item.getpId(),item.getiCount(),item.getiNum());
        } finally {
            DruidUtils.closeAll(null, null, connection);
        }
    }
}
