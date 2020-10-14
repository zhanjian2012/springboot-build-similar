package com.springboot.similar.factory;

import com.springboot.similar.annoation.ioc.Autowired;
import com.springboot.similar.annoation.ioc.Compnent;
import com.springboot.similar.annoation.springmvc.RestController;
import com.springboot.similar.annoation.springmvc.Service;
import com.springboot.similar.util.CollectionUtils;
import com.springboot.similar.util.RefectUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * beanFactory
 */
public final class BeanFactory {

    private static Map<String, Object> BEAN_MAP = new HashMap<>();

    public static void loadAllBeans(String packagePath) {
        Set<Class<?>> restControllerSet = RefectUtils.scanClass(packagePath, RestController.class);
        Set<Class<?>> componentSet = RefectUtils.scanClass(packagePath, Compnent.class);
        if (CollectionUtils.isNotEmpty(componentSet)) {
            for (Class<?> clazz : componentSet) {
                BEAN_MAP.put(clazz.getName(), RefectUtils.getInstance(clazz));
            }
        }
        if (CollectionUtils.isNotEmpty(restControllerSet)) {
            for (Class<?> clazz : restControllerSet) {
                BEAN_MAP.put(clazz.getName(), RefectUtils.getInstance(clazz));
            }
        }
        Set<Class<?>> serviceSet = RefectUtils.scanClass(packagePath, Service.class);
        if (CollectionUtils.isNotEmpty(serviceSet)) {
            for (Class<?> clazz : serviceSet) {
                BEAN_MAP.put(clazz.getName(), RefectUtils.getInstance(clazz));
            }
        }
        try {
            for (String key : BEAN_MAP.keySet()) {
                Object instance = BEAN_MAP.get(key);
                Field[] fields = instance.getClass().getDeclaredFields();
                for (Field field : fields) {
                    Autowired annotation = field.getAnnotation(Autowired.class);
                    if (annotation != null) {
                        Class<?> type = field.getType();
                        if (type.isInterface()) {
                            for (Class s : serviceSet) {
                                Class<?>[] interfaces = s.getInterfaces();
                                for (Class inter : interfaces) {
                                    if (inter.isAssignableFrom(type)) {
                                        field.setAccessible(true);
                                        field.set(instance, getBean(s.getName()));
                                    }
                                }
                            }
                        } else {
                            field.setAccessible(true);
                            String name = field.getType().getName();
                            field.set(instance, getBean(name));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取bean
     * @param className 类名
     * @return Object
     */
    public static Object getBean(String className) {
        return BEAN_MAP.get(className);
    }

}
