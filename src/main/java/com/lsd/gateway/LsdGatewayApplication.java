package com.lsd.gateway;


import com.lsd.gateway.handler.ChildChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class LsdGatewayApplication {

    public static void main(String[] args){


        EventLoopGroup bossLoop = new NioEventLoopGroup();
        EventLoopGroup workLoop = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap
                    .group(bossLoop, workLoop)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChildChannelHandler());



            //绑定端口，同步等待成功
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();

            //等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();

        }catch (Exception e){

            e.printStackTrace();
        }


    }



}
