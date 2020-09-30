package com.pan.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 读取配置文件
 * @author tangpan
 */
public class PropertyUtil {

    @SuppressWarnings("rawtypes")
    private static Map map = null;

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void loadFile() {
        map = new HashMap();
        try {
            Properties p = new Properties();
            p.load(PropertyUtil.class.getClassLoader().getResourceAsStream("parame.properties"));
            Iterator it = p.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = p.getProperty(key);
                map.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String str) {
        if (map == null) {
            loadFile();
        }
        return (String) map.get(str);
    }

}
