package com.lsd.gateway.handler;

import com.lsd.gateway.filter.DefaultGatewayFilterChain;
import com.lsd.gateway.filter.GatewayFilter;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-04 21:46
 * @Modified By：
 */
public class DefaultWebFilterHandler {

    private final List<GatewayFilter> globalFilters;

    public DefaultWebFilterHandler(List<GatewayFilter> globalFilters) {
        this.globalFilters = globalFilters;
    }


    public void handle(FullHttpRequest request) {

        new DefaultGatewayFilterChain(globalFilters).filter(request);

    }
}
