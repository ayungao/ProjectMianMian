package com.itheima.mm.utils;

/**
 * @description:
 * @author: AyuNGao
 * @date: 21/4/4 14:15
 */
public class IntegerUtils {
    public static Integer parseInteger(Object object) {
        if (object instanceof Integer) {
            return (Integer) object;
        }else {
            return Integer.valueOf((String) object);
        }
    }
}
