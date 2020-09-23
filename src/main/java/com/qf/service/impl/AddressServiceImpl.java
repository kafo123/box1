package com.qf.service.impl;

import com.qf.dao.AddressDao;
import com.qf.dao.impl.AddressDaoImpl;
import com.qf.entity.Address;
import com.qf.service.AddressService;
import com.qf.util.DruidUtils;
import com.qf.util.SysContant;

import java.sql.SQLException;
import java.util.List;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 10:41
 */
public class AddressServiceImpl implements AddressService {
    AddressDao addressDao = new AddressDaoImpl();
    @Override
    public List<Address> getAllAddress(int uid) throws SQLException {
        return addressDao.findAllAddress(uid);
    }

    @Override
    public int addAddress(Address address) throws SQLException {
        List<Address> allAddress = addressDao.findAllAddress(address.getuId());
        if(allAddress.size()>0) {
            return addressDao.insertAddress(address);
        }else {
            address.setaState(SysContant.AddressState.DEFAULT_ADDRESS.getValue());
            return addressDao.insertAddress(address);
        }
    }

    @Override
    public int removeAddress(int aid) throws SQLException {
        return addressDao.deleteAddress(aid);
    }

    @Override
    public int updateAddressStateByAidAndUid(int aid, int uid) throws SQLException {
        List<Address> allAddress = addressDao.findAllAddress(uid);
        int i = 0;
        try {
            DruidUtils.beginTransaction();
            if(allAddress.size()>0){
                for (Address address : allAddress) {
                    if(address.getaState()==SysContant.AddressState.DEFAULT_ADDRESS.getValue()){
                       i=addressDao.updateAddressStateByAid(address.getaId(), SysContant.AddressState.NOT_DEFAULT_ADDRESS.getValue());
                    }
                }
            }
            i+=addressDao.updateAddressStateByAid(aid,SysContant.AddressState.DEFAULT_ADDRESS.getValue());
            DruidUtils.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DruidUtils.rollback();
        }finally {
            DruidUtils.close();
        }
        return i;
    }

    @Override
    public int updateAddress(Address address) throws SQLException {
        return addressDao.updateAddress(address);
    }
}
