package com.qf.service;

import com.qf.entity.Type;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/15
 * @TIME 16:07
 */
public interface GoodService {
    List<Type> getAllType()throws SQLException;

    int addType(Type type) throws SQLException;
}
