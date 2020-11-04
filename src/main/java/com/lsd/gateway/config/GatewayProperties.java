package com.lsd.gateway.config;

import com.lsd.gateway.route.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-02 22:42
 * @Modified By：
 */
public class GatewayProperties {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayProperties.class);

    private List<RouteDefinition> routes = new ArrayList<>();

    public void setRoutes(List<RouteDefinition> routes) {
        this.routes = routes;
        if (routes != null && routes.size() > 0 && LOG.isDebugEnabled()) {
            LOG.debug("Routes supplied from Gateway Properties: " + routes);
        }
    }

    public List<RouteDefinition> getRoutes() {
        return routes;
    }
}
