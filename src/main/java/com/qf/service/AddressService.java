package com.qf.service;

import com.qf.entity.Address;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 10:40
 */
public interface AddressService {

    List<Address> getAllAddress(int uid) throws SQLException;

    int addAddress(Address address) throws SQLException;

    int removeAddress(int aid) throws SQLException;

    int updateAddressStateByAidAndUid(int aid, int uid) throws SQLException;

    int updateAddress(Address address) throws SQLException;


}
