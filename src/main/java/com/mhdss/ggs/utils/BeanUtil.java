package com.mhdss.ggs.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {

    public static <T> Map BeanToMap(T bean) {
        Map map = new HashMap();
        Class clazz = bean.getClass();
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            setMap(field, bean, map);
        });
        return map;
    }


    public static <T> Map BeanToMap(T bean, String fields) {
        Map map = new HashMap();
        Class clazz = bean.getClass();
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> fields.indexOf(field.getName()) != -1 ? true : false)
                .forEach(field -> setMap(field, bean, map));
        return map;
    }

    /**
     * 获取字段的值map
     *
     * @param field
     */
    public static <T> void setMap(Field field, T bean, Map map) {
        //访问private限制
        field.setAccessible(true);
        try {
            Object fieldValue = field.get(bean);
            if (fieldValue != null) {
                map.put(field.getName(), fieldValue);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
