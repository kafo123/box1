package com.qf.util;

import java.util.Random;

/**
 * @author kafo
 * @description
 * @date 2020/9/14
 * @TIME 21:24
 */
public class ActiveCodeUtil {
    public static String getActiveCode(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            str.append((char)(new Random().nextInt(26)+'A'));
        }
        return str.toString();
    }
}
