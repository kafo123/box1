package com.qf.util;

/**
 * @author kafo
 * @description
 * @date 2020/9/14
 * @TIME 20:24
 */
public class SysContant {
    public static final int NO_ACTIVE = 0;
    public static final int ACTIVE = 1;
    public static final int CUSTOMER=0;

    public static final String FORWORD = "forword";
    public static final String REDIRECT = "redirect";
    public static final String FLAG = ":";

    public static enum OrderState{
        NOT_PAY(0,"not_pay"),PAID_NOT_SEND(1,"paid_not_send"),
        SENDED_NOT_RECEIVE(2,"sended_not_receive"),RECEIVED_NOT_COMMENT(3,"received_not_comment"),
        ORDER_FINISHED(4,"order_finished"),ORDER_BACK(5,"order_back");
        private int value;
        private String des;
        private OrderState(int value, String des){
            this.value = value;
            this.des = des;
        }

        public int getValue() {
            return value;
        }
        public String getDes() {
            return des;
        }
    }

    public static enum AddressState{
        DEFAULT_ADDRESS(1,"default_address"),NOT_DEFAULT_ADDRESS(0,"not_default_address");
        private int value;
        private String des;

        private AddressState(int value, String des) {
            this.value = value;
            this.des = des;
        }

        public int getValue() {
            return value;
        }

        public String getDes() {
            return des;
        }
    }

    public static final int NORMAL_USER = 0;
    public static final int ADMIN = 1;

}
