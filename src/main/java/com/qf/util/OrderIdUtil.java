package com.qf.util;

/**
 * @author kafo
 * @description
 * @date 2020/9/17
 * @TIME 12:17
 */
public class OrderIdUtil {
    public static String createOrderId(int uid){
        return System.currentTimeMillis()+""+uid;
    }
}
