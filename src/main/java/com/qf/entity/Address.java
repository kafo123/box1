package com.qf.entity;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 10:28
 */
public class Address {
    private int aId;
    private int uId;
    private String aName;
    private String aPhone;
    private String aDetail;
    private int aState;

    public Address() {
    }

    public Address(int aId, int uId, String aName, String aPhone, String aDetail, int aState) {
        this.aId = aId;
        this.uId = uId;
        this.aName = aName;
        this.aPhone = aPhone;
        this.aDetail = aDetail;
        this.aState = aState;
    }

    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public int getaState() {
        return aState;
    }

    public void setaState(int aState) {
        this.aState = aState;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getaPhone() {
        return aPhone;
    }

    public void setaPhone(String aPhone) {
        this.aPhone = aPhone;
    }

    public String getaDetail() {
        return aDetail;
    }

    public void setaDetail(String aDetail) {
        this.aDetail = aDetail;
    }
}
