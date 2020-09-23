package com.qf.dao;

import com.qf.entity.Address;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 10:33
 */
public interface AddressDao {
    /**
     * 查询全部的地址信息
     * @param uid 用户id
     * @return 用于保存全部地址信息的列表
     * @throws SQLException
     */
    List<Address> findAllAddress(int uid) throws SQLException;

    int insertAddress(Address address) throws SQLException;

    int deleteAddress(int aid) throws SQLException;

    int updateAddressStateByAid(int aid, int aState) throws SQLException;

    int updateAddress(Address address) throws SQLException;


}
