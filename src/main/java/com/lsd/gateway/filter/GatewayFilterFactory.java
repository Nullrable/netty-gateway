package com.lsd.gateway.filter;

import com.lsd.gateway.predicate.FilterDefinition;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-03 22:05
 * @Modified By：
 */
public interface GatewayFilterFactory {

    GatewayFilter apply(String routeId, FilterDefinition filterDefinition);
}
