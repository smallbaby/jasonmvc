package com.jason.util;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.lang.reflect.Method;

/**
 * author: zhangkai
 * date: 2019-08-23
 * description:
 */
public class ReflectUtil {
    public static Object newInstance(String className) throws ClassNotFoundException {
        Object object = Class.forName(className);
        if (object != null) {
            return object;
        }
        return null;
    }

    public static Object invokeMethod(Object bean, Method method, Object... args) {
        try {
            Class<?>[] types = method.getParameterTypes();
            int argCount = args == null ? 0 : args.length;

            ExceptionUtil.markRunTimeWhen(argCount != types.length, "%s in %s", method.getName(), bean);
            for (int i = 0; i < argCount; i++) {
                args[i] = cast(args[i], types[i]);
            }
            return method.invoke(bean, args);

        } catch (Exception e) {
            ExceptionUtil.makeRuntime(e);
        }
        return null;
    }

    public static <T> T cast(Object value, Class<T> type) {
        if (value != null && !type.isAssignableFrom(value.getClass())) {
            if (is(type, int.class, Integer.class)) {
                value = Integer.parseInt(String.valueOf(value));
            } else if (is(value, long.class, Long.class)) {
                value = Long.parseLong(String.valueOf(value));
            } else if (is(value, float.class, Float.class)) {
                value = Float.parseFloat(String.valueOf(value));
            } else if (is(value, double.class, Double.class)) {
                value = Double.parseDouble(String.valueOf(value));
            } else if (is(value, boolean.class, Boolean.class)) {
                value = Boolean.parseBoolean(String.valueOf(value));
            } else if (is(type, String.class)) {
                value = String.valueOf(value);
            }
        }
        return (T) value;
    }

    public static boolean is(Object obj, Object... mybe) {
        if (obj != null && mybe != null) {
            for (Object mb : mybe) {
                if (obj.equals(mb)) {
                    return true;
                }
            }
        }
        return false;
    }

}
