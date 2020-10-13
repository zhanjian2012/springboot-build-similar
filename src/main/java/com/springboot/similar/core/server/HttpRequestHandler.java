package com.springboot.similar.core.server;

import com.google.gson.Gson;
import com.springboot.similar.core.handler.GetMappingHandler;
import com.springboot.similar.core.handler.MappingHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        if (req.method().name().equals(HttpMethod.GET.name())) {
            MappingHandler mappingHandler = new GetMappingHandler();
            Object handler = mappingHandler.handler(req);
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(new Gson().toJson(handler).getBytes()));
            // 设置头信息
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            // 将html write到客户端
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
