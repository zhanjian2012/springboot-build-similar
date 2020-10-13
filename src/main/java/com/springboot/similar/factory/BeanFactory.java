package com.springboot.similar.factory;

import com.springboot.similar.annoation.ioc.Compnent;
import com.springboot.similar.annoation.springmvc.RestController;
import com.springboot.similar.util.CollectionUtils;
import com.springboot.similar.util.RefectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * beanFactory
 */
public final class BeanFactory {

    public static final Map<String,Object> beanMap = new HashMap<String, Object>();

    public static void loadAllBeans(String packagePath) {
        Set<Class<?>> restControllerSet = RefectUtils.scanClass(packagePath, RestController.class);
        Set<Class<?>> componentSet = RefectUtils.scanClass(packagePath, Compnent.class);

        if(CollectionUtils.isNotEmpty(restControllerSet)) {
            for(Class<?> clazz : restControllerSet) {
                beanMap.put(clazz.getName(), RefectUtils.getInstance(clazz));
            }
        }

        if(CollectionUtils.isNotEmpty(componentSet)) {
            for(Class<?> clazz : componentSet) {
                beanMap.put(clazz.getName(), RefectUtils.getInstance(clazz));
            }
        }
    }

    public static Object getBean(String className) {
        return beanMap.get(className);
    }

    public static void main(String[] args) {
        loadAllBeans("com.netty");
    }


}
