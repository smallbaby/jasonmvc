package com.jason.util;

import java.util.Arrays;

/**
 * author: zhangkai
 * date: 2019-08-23
 * description:
 */
public class ExceptionUtil {
    public static void markRunTimeWhen(boolean flag, String message, Object... args) {
        if (flag) {
            message = String.format(message, args);
            RuntimeException e = new RuntimeException(message);
            throw correctStackTrace(e);
        }
    }

    public static void makeRuntime(Throwable cause) {
        RuntimeException e = new RuntimeException(cause);
        throw correctStackTrace(e);
    }

    private static RuntimeException correctStackTrace(RuntimeException e) {
        StackTraceElement[] s = e.getStackTrace();
        if (s != null && s.length > 0) {
            e.setStackTrace(Arrays.copyOfRange(s, 1, s.length));
        }
        return e;
    }
}
