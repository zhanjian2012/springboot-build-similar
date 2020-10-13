package com.springboot.similar.core.handler;

import io.netty.handler.codec.http.FullHttpRequest;

public interface MappingHandler {

    Object handler(FullHttpRequest request) throws Exception;
}
