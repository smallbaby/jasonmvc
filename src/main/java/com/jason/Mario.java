package com.jason;

import com.jason.config.ConfigLoader;
import com.jason.render.JspRender;
import com.jason.render.Render;
import com.jason.route.Routers;
import com.jason.servlet.wrapper.Request;
import com.jason.servlet.wrapper.Response;

import java.lang.reflect.Method;

/**
 * author: zhangkai
 * date: 2019-08-22
 * description:
 */
public class Mario {

    /**
     * 存放所有路由
     */
    private Routers routers;

    /**
     * 配置加载器
     */
    private ConfigLoader configLoader;

    /**
     * 框架是否已经初始化
     */
    private boolean init = false;

    private Render render;


    public Mario() {
        routers = new Routers();
        configLoader = new ConfigLoader();
        render = new JspRender();
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    private static class MarioHolder {
        private static Mario ME = new Mario();
    }

    public static Mario me() {
        return MarioHolder.ME;
    }

    public Mario loadConf(String conf) {
        configLoader.load(conf);
        return this;
    }

    public Mario setConf(String name, String value) {
        configLoader.setConf(name, value);
        return this;
    }

    public Mario addRoutes(Routers routers) {
        this.routers.addRoute(routers.getRoutes());
        return this;
    }

    public Routers getRouters() {
        return routers;
    }

    /**
     * 添加路由
     * @param path       映射的PATH
     * @param methodName 方法
     * @param controller 控制器对象
     * @return 返回Mario
     */
    public Mario addRoute(String path, String methodName, Object controller) {
        try {
            Method method = controller.getClass().getMethod(methodName, Request.class, Response.class);
            this.routers.addRoute(path, method, controller);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Render getRender() {
        return render;
    }

    public void setRender(Render render) {
        this.render = render;
    }

    public String getConf(String name) {
        return configLoader.getConf(name);
    }
}
















