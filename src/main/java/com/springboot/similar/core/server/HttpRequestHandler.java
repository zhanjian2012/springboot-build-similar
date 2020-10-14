package com.springboot.similar.core.server;

import com.google.gson.Gson;
import com.springboot.similar.common.Result;
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
            Object handler = null;
            try {
                handler = mappingHandler.handler(req);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(handler == null) {
                handler = Result.error(500, "服务器内部错误");
            }
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
