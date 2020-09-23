package com.qf.util;

/**
 * @author kafo
 * @description
 * @date 2020/9/19
 * @TIME 9:52
 */
public class StringUtil {
    /**
     * 判断字符串是否为空
     * @param str 要验证的字符串
     * @return ture:该字符串为空 false：该字符串不为空
     */
    public static boolean isEmpty(String str){
        return "".equals(str)||str==null;
    }
}
