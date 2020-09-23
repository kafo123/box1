package com.qf.service.impl;

import com.qf.dao.GoodDao;
import com.qf.dao.impl.GoodDaoImpl;
import com.qf.entity.Type;
import com.qf.service.GoodService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/15
 * @TIME 16:08
 */
public class GoodServiceImpl implements GoodService {
    private GoodDao goodDao = new GoodDaoImpl();
    @Override
    public List<Type> getAllType() throws SQLException {
        return goodDao.findAllType();
    }

    @Override
    public int addType(Type type) throws SQLException {
        return goodDao.insertType(type);
    }
}
