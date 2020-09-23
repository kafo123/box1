package com.qf.entity;

import java.math.BigDecimal;

/**
 * @author kafo
 * @description
 * @date 2020/9/16
 * @TIME 13:45
 */
public class Cart {
    private int cId;
    private int uId;
    private int pId;
    private BigDecimal cCount;
    private int cNum;

    private Product product;

    public Cart() {
    }

    public Cart(int cId, int uId, int pId, BigDecimal cCount, int cNum) {
        this.cId = cId;
        this.uId = uId;
        this.pId = pId;
        this.cCount = cCount;
        this.cNum = cNum;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public BigDecimal getcCount() {
        return cCount;
    }

    public void setcCount(BigDecimal cCount) {
        this.cCount = cCount;
    }

    public int getcNum() {
        return cNum;
    }

    public void setcNum(int cNum) {
        this.cNum = cNum;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cId=" + cId +
                ", uId=" + uId +
                ", pId=" + pId +
                ", cCount=" + cCount +
                ", cNum=" + cNum +
                ", product=" + product +
                '}';
    }
}
