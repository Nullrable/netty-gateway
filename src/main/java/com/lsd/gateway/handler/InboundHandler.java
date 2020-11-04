package com.lsd.gateway.handler;


import com.lsd.gateway.config.GatewayProperties;
import com.lsd.gateway.filter.DefaultGatewayFilterChain;
import com.lsd.gateway.filter.GatewayFilter;
import com.lsd.gateway.loadbalance.LoadBalance;
import com.lsd.gateway.predicate.Predicate;
import com.lsd.gateway.predicate.PredicateDefinition;
import com.lsd.gateway.route.Route;
import com.lsd.gateway.route.RouteDefinition;
import com.lsd.gateway.route.RouteFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-03 21:39
 * @Modified By：
 */
public class InboundHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final GatewayProperties gatewayProperties = new GatewayProperties();

    private LoadBalance loadBalance;

    static {

        List<RouteDefinition> routeDefinitionList = new ArrayList<>();

        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId("lsd1");

        List<PredicateDefinition> predicateDefinitions = new ArrayList<>();
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName("HOST");
        Map<String, String> args = new HashMap<>();
        args.put("HOST", "baidu.com");
        predicateDefinition.setArgs(args);
        predicateDefinitions.add(predicateDefinition);

        routeDefinition.setPredicates(predicateDefinitions);


        try {
            routeDefinition.setUri(new URI("http://baidu.com"));
        }catch (Exception e){
            e.printStackTrace();
        }

        routeDefinitionList.add(routeDefinition);


        gatewayProperties.setRoutes(routeDefinitionList);
    }

    private RouteFactory routeFactory = new RouteFactory(gatewayProperties);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {



        List<Route> routeList = routeFactory.getRoutes();


        Route route = getRoute(request, routeList);

        if(route == null){


            String msg =  "you not match route \r\n" ;

            ByteBuf outByteBuf = Unpooled.copiedBuffer(msg,  Charset.forName("UTF-8"));

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, outByteBuf);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, msg.getBytes().length);
            ctx.write(response);

            // 写入文件尾部
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            future.addListener(ChannelFutureListener.CLOSE);

            return;
        }

        List<GatewayFilter> gatewayFilters = route.getGatewayFilters();

        if ( !CollectionUtils.isEmpty(gatewayFilters )) {
            DefaultWebFilterHandler defaultWebFilterHandler = new DefaultWebFilterHandler(gatewayFilters);
            defaultWebFilterHandler.handle(request);
        }

        service(route, ctx, request);

    }



    private Route getRoute(FullHttpRequest request, List<Route> routeList) {

        //TODO 这部分可以写的优雅

        if ( loadBalance != null ) {

            return loadBalance.match(routeList);
        }

        String predicateStr = request.headers().get("Host");

        for (Route route : routeList) {

            List<Predicate> predicates = route.getPredicates();

            for( Predicate predicate : predicates){

                if(predicate.match(predicateStr)){
                    return route;
                }
            }
        }

        return null;

    }

    private void service(Route route, ChannelHandlerContext ctx, FullHttpRequest request){

        String proxyReponse = doProxyRequest(route, request);

        ByteBuf outByteBuf = Unpooled.copiedBuffer(proxyReponse,  Charset.forName("UTF-8"));

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, outByteBuf);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, proxyReponse.getBytes().length);

        ctx.write(response);

        // 写入文件尾部
        ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        future.addListener(ChannelFutureListener.CLOSE);

    }


    private String doProxyRequest(Route route, FullHttpRequest request) {

        //TODO 改造成池
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();


        HttpUriRequest httpRequest = null;

        if (request.method().equals(HttpMethod.GET)) {

            // 创建Get请求
            httpRequest = new HttpGet(route.getUri());

        } else if (request.method().equals(HttpMethod.GET)) {

            httpRequest = new HttpPost(route.getUri());
        }


        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpRequest);
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            return result;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}