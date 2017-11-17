package com.chm.chmframwork.utils;

/**
 * object强制转换
 * Created by chenmin on 2017/11/17.
 */

public class ValueUtils {

    public static <T> T getValue(Object value, T defaultValue) {
        try {
            return value == null ? defaultValue : (T) value;
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
