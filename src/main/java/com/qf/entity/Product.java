package com.qf.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author kafo
 * @description
 * @date 2020/9/15
 * @TIME 16:20
 */
public class Product {
    private int pId;
    private int tId;
    private String pName;
    private Date pTime;
    private String pImage;
    private BigDecimal pPrice;
    private int pState;
    private String pInfo;

    private String tName;

    public Product() {
    }

    public Product(int pId, int tId, String pName, Date pTime, String pImage, BigDecimal pPrice, int pState, String pInfo) {
        this.pId = pId;
        this.tId = tId;
        this.pName = pName;
        this.pTime = pTime;
        this.pImage = pImage;
        this.pPrice = pPrice;
        this.pState = pState;
        this.pInfo = pInfo;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Date getpTime() {
        return pTime;
    }

    public void setpTime(Date pTime) {
        this.pTime = pTime;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public BigDecimal getpPrice() {
        return pPrice;
    }

    public void setpPrice(BigDecimal pPrice) {
        this.pPrice = pPrice;
    }

    public int getpState() {
        return pState;
    }

    public void setpState(int pState) {
        this.pState = pState;
    }

    public String getpInfo() {
        return pInfo;
    }

    public void setpInfo(String pInfo) {
        this.pInfo = pInfo;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pId=" + pId +
                ", tId=" + tId +
                ", pName='" + pName + '\'' +
                ", pTime=" + pTime +
                ", pImage='" + pImage + '\'' +
                ", pPrice=" + pPrice +
                ", pState=" + pState +
                ", pInfo='" + pInfo + '\'' +
                ", tName='" + tName + '\'' +
                '}';
    }
}
