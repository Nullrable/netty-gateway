package com.lsd.gateway.loadbalance;

import com.lsd.gateway.route.Route;

import java.util.List;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-04 22:19
 * @Modified By：
 */
public class RandomLoadBalance implements LoadBalance {

    private final List<Route> routes;

    public RandomLoadBalance ( List<Route> routes ) {
        this.routes = routes;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public Route match(List<Route> routeList){

        //TODO 随机返回符合的一个route

        return null;
    }
}
