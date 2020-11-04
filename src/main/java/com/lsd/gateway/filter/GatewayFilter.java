package com.lsd.gateway.filter;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-02 22:37
 * @Modified By：
 */
public interface GatewayFilter {

    void filter(FullHttpRequest request, GatewayFilterChain chain);

}
