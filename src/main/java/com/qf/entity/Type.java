package com.qf.entity;

/**
 * @author kafo
 * @description
 * @date 2020/9/15
 * @TIME 16:00
 */
public class Type {
    private int tId;
    private String tName;
    private String tInfo;

    public Type() {
    }

    public Type(int tId, String tName, String tInfo) {
        this.tId = tId;
        this.tName = tName;
        this.tInfo = tInfo;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettInfo() {
        return tInfo;
    }

    public void settInfo(String tInfo) {
        this.tInfo = tInfo;
    }
}
