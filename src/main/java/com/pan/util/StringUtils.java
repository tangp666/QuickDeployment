package com.pan.util;

import freemarker.template.utility.StringUtil;

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
