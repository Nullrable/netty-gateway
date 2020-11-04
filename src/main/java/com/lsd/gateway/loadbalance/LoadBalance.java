package com.lsd.gateway.loadbalance;

import com.lsd.gateway.route.Route;

import java.util.List;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-04 22:30
 * @Modified By：
 */
public interface LoadBalance {


    Route match(List<Route> routeList);
}
