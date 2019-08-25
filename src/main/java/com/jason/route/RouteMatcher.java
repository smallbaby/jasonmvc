package com.jason.route;

import com.jason.util.PathUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * author: zhangkai
 * date: 2019-08-22
 * description:
 */
public class RouteMatcher {
    private List<Route> routes;

    public RouteMatcher(List<Route> routes) {
        this.routes = routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    /**
     * 根据path找路由
     * @param path
     * @return
     */
    public Route findRoute(String path) {
        String cleanPath = parsePath(path);
        List<Route> matchRoutes = new ArrayList<>();
        for (Route route : this.routes) {
            if (matchesPath(route.getPath(), cleanPath)) {
                matchRoutes.add(route);
            }
        }
        giveMatch(path, matchRoutes);
        return matchRoutes.size() > 0 ? matchRoutes.get(0) : null;
    }

    private void giveMatch(final String uri, List<Route> routes) {
        Collections.sort(routes, new Comparator<Route>() {
            @Override
            public int compare(Route o1, Route o2) {
                if (o2.getPath().equals(uri)) {
                    return o2.getPath().indexOf(uri);
                }
                return -1;
            }
        });
    }

    private boolean matchesPath(String routePath, String pathToMatch) {
        routePath = routePath.replaceAll(PathUtil.VAR_REGEXP, PathUtil.VAR_REPLACE);
        return pathToMatch.matches("(?i)" + routePath);
    }

    private String parsePath(String path) {
        path = PathUtil.fixPath(path);
        try {
            URI uri = new URI(path);
            return uri.getPath();
        } catch (URISyntaxException e) {
            return null;
        }
    }


}











