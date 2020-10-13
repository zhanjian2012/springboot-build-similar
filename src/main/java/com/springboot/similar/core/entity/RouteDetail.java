package com.springboot.similar.core.entity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RouteDetail {

    private String uri;

    private Method method;

    private String className;

    private List<Paramter> paramterList = new ArrayList<>();

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Paramter> getParamterList() {
        return paramterList;
    }

    public void setParamterList(List<Paramter> paramterList) {
        this.paramterList = paramterList;
    }
}
