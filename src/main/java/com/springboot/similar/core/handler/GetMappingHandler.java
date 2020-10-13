package com.springboot.similar.core.handler;

import com.springboot.similar.core.entity.RouteDetail;
import com.springboot.similar.factory.BeanFactory;
import com.springboot.similar.factory.RouteFactory;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

public class GetMappingHandler implements MappingHandler {

    private static Object[] buildParameters(FullHttpRequest request) {
        List<Object> objectList = new ArrayList<>();
        String uri = request.uri();
        if (uri.contains("?")) {
            String split = uri.split("\\?")[1];
            if (split.contains("&")) {
                for (String param : split.split("&")) {
                    String s = param.split("=")[1];
                    objectList.add(s);
                }
            }
        }
        return objectList.toArray();
    }

    @Override
    public Object handler(FullHttpRequest request) throws Exception {
        String requestType = request.method().name();
        if (!requestType.equals(HttpMethod.GET.name())) {
            throw new Exception("request type is error, require get, but is " + requestType);
        }
        RouteDetail routeDetail = RouteFactory.getRouteDetail(request);
        if (routeDetail == null) {
            return null;
        }
        return routeDetail.getMethod().invoke(BeanFactory.getBean(routeDetail.getClassName()), buildParameters(request));
    }

}
