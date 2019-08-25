package com.jason.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * author: zhangkai
 * date: 2019-08-22
 * description:
 */
public class PathUtil {
    public static final String VAR_REGEXP = ":(\\w+)";
    public static final String VAR_REPLACE = "([^#/?]+)";

    private static final String SLASH = "/";

    public static String getRelativePath(HttpServletRequest request) {
        String path = request.getRequestURI();
        String contextPath = request.getContextPath();
        path = path.substring(contextPath.length());
        if (path.length() > 0) {
            path = path.substring(1);
        }

        if (!path.startsWith(SLASH)) {
            path = SLASH + path;
        }

        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return path;
    }

    public static boolean isParam(String routePart) {
        return routePart.startsWith(":");
    }

    public static String fixPath(String path) {
        if (path == null) {
            return "/";
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    public static String cleanPath(String path) {
        return path == null ? path : path.replaceAll("[/]+", "/");
    }


}
