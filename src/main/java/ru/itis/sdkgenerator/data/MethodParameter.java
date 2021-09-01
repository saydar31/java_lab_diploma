package ru.itis.sdkgenerator.data;

import java.lang.reflect.Type;

public class MethodParameter {
    private Type type;
    private String requestName;
    private String javaParameterName;
    private MethodParameterType methodParameterType;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getJavaParameterName() {
        return javaParameterName;
    }

    public void setJavaParameterName(String javaParameterName) {
        this.javaParameterName = javaParameterName;
    }

    public MethodParameterType getMethodParameterType() {
        return methodParameterType;
    }

    public void setMethodParameterType(MethodParameterType methodParameterType) {
        this.methodParameterType = methodParameterType;
    }
}
