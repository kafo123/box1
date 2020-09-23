package com.qf.dao;

import com.qf.entity.Type;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/15
 * @TIME 16:03
 */
public interface GoodDao {

    List<Type> findAllType() throws SQLException;

    int insertType(Type type) throws SQLException;
}
