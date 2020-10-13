package com.springboot.similar.factory;

import com.springboot.similar.annoation.springmvc.GetMapping;
import com.springboot.similar.annoation.springmvc.PostMapping;
import com.springboot.similar.annoation.springmvc.RestController;
import com.springboot.similar.core.entity.Paramter;
import com.springboot.similar.core.entity.RouteDetail;
import com.springboot.similar.util.RefectUtils;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * 路由映射
 * @author zhanjian
 * @date 2020年10月13日09:51:35
 */
public class RouteFactory {

    private static final Map<String, RouteDetail> GET_URI_MAP = new HashMap<>();
    private static final Map<String, RouteDetail> POST_URI_MAP = new HashMap<>();

    public static void loadAllUri(String packagePath) {
        Set<Class<?>> restControllerSet = RefectUtils.scanClass(packagePath, RestController.class);
        for (Class<?> clazz : restControllerSet) {
            RestController restController = clazz.getAnnotation(RestController.class);
            String baseUri = restController.value();
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                Annotation[] annotations = method.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof GetMapping) {
                        GetMapping getMapping = (GetMapping) annotation;
                        RouteDetail routeDetail = buildRouteDetail(clazz, baseUri, method, getMapping.value());
                        GET_URI_MAP.put(baseUri + getMapping.value(), routeDetail);
                    } else if (annotation instanceof PostMapping) {
                        PostMapping postMapping = (PostMapping) annotation;
                        RouteDetail routeDetail = buildRouteDetail(clazz, baseUri, method, postMapping.value());
                        POST_URI_MAP.put(baseUri + postMapping.value(), routeDetail);
                    }
                }
            }
        }
    }

    private static RouteDetail buildRouteDetail(Class<?> clazz, String baseUri, Method method, String value) {
        RouteDetail routeDetail = new RouteDetail();
        routeDetail.setUri(baseUri + value);
        routeDetail.setClassName(clazz.getName());
        routeDetail.setMethod(method);
        Parameter[] parameters = method.getParameters();
        if (parameters != null) {
            List<Paramter> paramterList = new ArrayList<>();
            for (Parameter parameter : parameters) {
                Paramter paramter = new Paramter();
                paramter.setName(parameter.getName());
                paramter.setType(parameter.getType());
                paramterList.add(paramter);
            }
            routeDetail.setParamterList(paramterList);
        }
        return routeDetail;
    }

    public static RouteDetail getRouteDetail(FullHttpRequest httpRequest) {
        if (httpRequest.method().name().equals(HttpMethod.GET.name())) {
            return GET_URI_MAP.get(httpRequest.uri().split("\\?")[0]);
        } else if (httpRequest.method().name().equals(HttpMethod.POST.name())) {
            return POST_URI_MAP.get(httpRequest.uri());
        }
        return null;
    }
}
