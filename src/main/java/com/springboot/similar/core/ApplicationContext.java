package com.springboot.similar.core;

import com.springboot.similar.common.Banner;
import com.springboot.similar.factory.BeanFactory;
import com.springboot.similar.factory.RouteFactory;
import lombok.SneakyThrows;

public class ApplicationContext {

    private static final String PACKAGE_PATH = "com.springboot.similar";

    @SneakyThrows
    public static void run() {

        // 1.扫描所有的RestController类
        BeanFactory.loadAllBeans(PACKAGE_PATH);

        // 2.路由信息加载
        RouteFactory.loadAllUri(PACKAGE_PATH);

        System.out.println(Banner.banner);

        // 8081为启动端口
        HttpServer server = new HttpServer(8081);
        server.start();
    }
}
