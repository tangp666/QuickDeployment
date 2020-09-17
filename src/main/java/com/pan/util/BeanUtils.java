package com.pan.util;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * spring管理bean
 * @author tangpan
 */
public class BeanUtils {

    public static ConfigurableApplicationContext applicationContext;

    public static <T> T getBean(Class<T> c){
        return applicationContext.getBean(c);
    }

}
