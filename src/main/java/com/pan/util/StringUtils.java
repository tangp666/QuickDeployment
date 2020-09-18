package com.pan.util;

/**
 * String对象方法
 * @author tangpan
 */
public class StringUtils {

    /**
     * 非空判断
     * @return
     */
    public static boolean isNotEmpty(Object object){
        if(object != null && !"".equals(object.toString().trim())){
            return true;
        }
        return false;
    }

}
