package com.jason.route;

import java.lang.reflect.Method;

/**
 * author: zhangkai
 * date: 2019-08-22
 * description:
 */
public class Route {

    private String path;
    private Method action;
    private Object controller;

    public Route() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Method getAction() {
        return action;
    }

    public void setAction(Method action) {
        this.action = action;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }
}
