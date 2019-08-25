package com.jason;

import com.jason.route.Route;
import com.jason.route.RouteMatcher;
import com.jason.route.Routers;
import com.jason.servlet.wrapper.Request;
import com.jason.servlet.wrapper.Response;
import com.jason.util.PathUtil;
import com.jason.util.ReflectUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * author: zhangkai
 * date: 2019-08-22
 * description:
 */
public class MarioFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(MarioFilter.class.getName());

    private RouteMatcher routeMatcher = new RouteMatcher(new ArrayList<Route>());

    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Mario mario = Mario.me();
        if (!mario.isInit()) {
            String className = filterConfig.getInitParameter("bootstrap");
            Bootstrap bootstrap = this.getBootstrap(className);
            bootstrap.init(mario);
            Routers routers = mario.getRouters();
            if (routers != null) {
                routeMatcher.setRoutes(routers.getRoutes());
            }
            servletContext = filterConfig.getServletContext();
            mario.setInit(true);
        }
    }

    private Bootstrap getBootstrap(String className) {
        if (className != null) {
            try {
                Class<?> clazz = Class.forName(className);
                Bootstrap bootstrap = (Bootstrap) clazz.newInstance();
                return bootstrap;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("init bootstrap class error");
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding(Const.DEFAULT_CHAR_SET);
        response.setCharacterEncoding(Const.DEFAULT_CHAR_SET);

        String uri = PathUtil.getRelativePath(request);
        LOGGER.info("Request URI: " + uri);
        Route route = routeMatcher.findRoute(uri);
        if (route != null) {
            handle(request, response, route);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Route route) {
        Request request = new Request(httpServletRequest);
        Response response = new Response(httpServletResponse);
        MarioContext.initContext(servletContext, request, response);

        Object controller = route.getController();
        Method actionMethod = route.getAction();
        executeMethod(controller, actionMethod, request, response);
    }

    private Object[] getArgs(Request request, Response response, Class<?>[] params) {
        int len = params.length;
        Object[] args = new Object[len];
        for (int i = 0; i < len; i++) {
            Class<?> paramTypeClazz = params[i];
            if (paramTypeClazz.getName().equals(Request.class.getName())) {
                args[i] = request;
            }
            if (paramTypeClazz.getName().equals(Response.class.getName())) {
                args[i] = response;
            }
        }
        return args;
    }

    private Object executeMethod(Object object, Method method, Request request, Response response) {
        int len = method.getParameterTypes().length;
        method.setAccessible(true);
        if (len > 0) {
            Object[] args = getArgs(request, response, method.getParameterTypes());
            return ReflectUtil.invokeMethod(object, method, args);
        } else {
            return ReflectUtil.invokeMethod(object, method);
        }
    }


    @Override
    public void destroy() {

    }
}
