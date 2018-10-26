package com.example.launcher.config;

import java.lang.reflect.Field;

class Utils {
    public static Field getDeclaredFieldRecursive(Class<?> clazz, String name){
        Field field;
        try {
            field = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return getDeclaredFieldRecursive(clazz.getSuperclass(), name);
        }
        return field;
    }
}
