package com.qf.entity;

import java.math.BigDecimal;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 12:05
 */
public class Item {
    private int iId;
    private String oId;
    private int pId;
    private BigDecimal iCount;
    private int iNum;

    private Product product;

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public BigDecimal getiCount() {
        return iCount;
    }

    public void setiCount(BigDecimal iCount) {
        this.iCount = iCount;
    }

    public int getiNum() {
        return iNum;
    }

    public void setiNum(int iNum) {
        this.iNum = iNum;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
