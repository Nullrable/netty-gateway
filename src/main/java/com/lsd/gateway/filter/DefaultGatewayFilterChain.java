package com.lsd.gateway.filter;

import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-04 21:16
 * @Modified By：
 */
public class DefaultGatewayFilterChain implements GatewayFilterChain {

    private final int index;

    private final List<GatewayFilter> filters;

    public DefaultGatewayFilterChain(List<GatewayFilter> filters) {
        this.filters = filters;
        this.index = 0;
    }

    private DefaultGatewayFilterChain(DefaultGatewayFilterChain parent, int index) {
        this.filters = parent.getFilters();
        this.index = index;
    }


    public List<GatewayFilter> getFilters() {
        return filters;
    }

    @Override
    public void filter(FullHttpRequest request) {
        if (this.index < filters.size()) {
            GatewayFilter filter = filters.get(this.index);
            DefaultGatewayFilterChain chain = new DefaultGatewayFilterChain(this,
                    this.index + 1);
            filter.filter(request, chain);
        }else {
            return; // complete
        }
    }

}
