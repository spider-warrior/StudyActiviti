package com.jy.activiti.common.util;

public class StringUtil {

    /**
     * 空字符串判断
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
