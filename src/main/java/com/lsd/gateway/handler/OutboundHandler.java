package com.lsd.gateway.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-03 21:44
 * @Modified By：
 */
public class OutboundHandler extends ChannelOutboundHandlerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(OutboundHandler.class);


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        ctx.write(msg, promise);
    }
}
