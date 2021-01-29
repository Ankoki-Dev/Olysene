package com.ankoki.olysene.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Validate {
    private Validate(){}

    public static void notNull(Object object, String message) {
        if (object == null) throw new NullPointerException(message);
    }

    public static void isTrue(boolean exprs, String message) {
        if (!exprs) throw new IllegalArgumentException(message);
    }

    public static void notEmpty(Object[] array, String message) {
        if (array == null || array.length == 0) throw new IllegalArgumentException(message);
    }

    public static void notEmpty(List list, String message) {
        if (list == null || list.isEmpty()) throw new IllegalArgumentException(message);
    }

    public static void notEmpty(Set set, String message) {
        if (set == null || set.isEmpty()) throw new IllegalArgumentException(message);
    }

    public static void notEmpty(Map map, String message) {
        if (map == null || map.isEmpty()) throw new IllegalArgumentException(message);
    }

    public static void notEmpty(Collection collection, String message) {
        if (collection == null || collection.isEmpty()) throw new IllegalArgumentException(message);
    }

    public static void isType(Object object, Class<?> clazz, String message) {
        if (!clazz.isInstance(object)) throw new IllegalArgumentException(message);
    }

    public static void isPositive(byte b, String message) {
        if (b <= 0) throw new IllegalArgumentException(message);
    }

    public static void isPositive(int i, String message) {
        if (i <= 0) throw new IllegalArgumentException(message);
    }

    public static void isPositive(double d, String message) {
        if (d <= 0) throw new IllegalArgumentException(message);
    }

    public static void isPositive(float f, String message) {
        if (f <= 0) throw new IllegalArgumentException(message);
    }

    public static void isPositive(long l, String message) {
        if (l <= 0) throw new IllegalArgumentException(message);
    }
}
