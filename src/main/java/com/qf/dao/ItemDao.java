package com.qf.dao;

import com.qf.entity.Item;

import java.sql.SQLException;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 13:52
 */
public interface ItemDao {
    int insertItem(Item item) throws SQLException;
}
