package com.lsd.gateway.filter;

import com.lsd.gateway.handler.OutboundHandler;
import com.lsd.gateway.predicate.FilterDefinition;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-03 22:01
 * @Modified By：
 */
public class SimpleGatewayFilterFactory implements GatewayFilterFactory {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleGatewayFilterFactory.class);


    public GatewayFilter apply(String routeId, FilterDefinition filterDefinition) {

        return new GatewayFilter() {
            @Override
            public void filter(FullHttpRequest request, GatewayFilterChain chain) {

                LOG.info("welcome simple gateway filter");

                chain.filter(request);
            }
        };
    }



}
