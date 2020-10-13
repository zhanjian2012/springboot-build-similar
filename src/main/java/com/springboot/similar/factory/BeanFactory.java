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

    public static final Map<String, Object> beanMap = new HashMap<String, Object>();

    public static void loadAllBeans(String packagePath) {
        Set<Class<?>> restControllerSet = RefectUtils.scanClass(packagePath, RestController.class);
        Set<Class<?>> componentSet = RefectUtils.scanClass(packagePath, Compnent.class);

        Set<Class<?>> ServiceSet = RefectUtils.scanClass(packagePath, Service.class);
        try {
            if (CollectionUtils.isNotEmpty(restControllerSet)) {
                for (Class<?> clazz : restControllerSet) {
                    Object instance = RefectUtils.getInstance(clazz);
                    beanMap.put(clazz.getName(), instance);

                    Field[] fields = clazz.getDeclaredFields();
                    if (fields != null) {
                        for (Field field : fields) {

                            Autowired annotation = field.getAnnotation(Autowired.class);
                            if (annotation != null) {
                                Class<?> type = field.getType();

                                for (Class s : ServiceSet) {
                                    Class<?>[] interfaces = s.getInterfaces();
                                    for (Class inter : interfaces) {
                                        if (inter.isAssignableFrom(type)) {
                                            field.setAccessible(true);
                                            field.set(instance, s.newInstance());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (CollectionUtils.isNotEmpty(componentSet)) {
            for (Class<?> clazz : componentSet) {
                beanMap.put(clazz.getName(), RefectUtils.getInstance(clazz));
            }
        }

        if (CollectionUtils.isNotEmpty(ServiceSet)) {
            for (Class<?> clazz : ServiceSet) {
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
