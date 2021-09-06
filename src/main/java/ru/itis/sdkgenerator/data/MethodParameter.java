package ru.itis.sdkgenerator.data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class MethodParameter {
    private Type type;
    private String requestName;
    private String javaParameterName;
    private MethodParameterType methodParameterType;
    private String modelPackage;

    public String getReturnTypeName() {
        if (type instanceof Class) {
            Class<?> aClass = (Class<?>) this.type;
            if (aClass.getPackage().getName().startsWith("java.")) {
                return aClass.getName();
            } else {
                return modelPackage + "." + aClass.getSimpleName();
            }
        } else {
            String stringBuilder = replacePackages(type, modelPackage);
            if (stringBuilder != null) return stringBuilder;
        }
        return Object.class.getName();
    }

    static String replacePackages(Type type, String modelPackage) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < actualTypeArguments.length; i++) {
                Type actualTypeArgument = actualTypeArguments[i];
                if (i > 0) {
                    stringBuilder.append("<");
                }
                if (actualTypeArgument.getTypeName().startsWith("java.")) {
                    stringBuilder.append(actualTypeArgument.getTypeName());
                } else {
                    int lastIndexOfDot = actualTypeArgument.getTypeName().lastIndexOf('.');
                    String simpleClassName = actualTypeArgument.getTypeName().substring(lastIndexOfDot);
                    stringBuilder.append(modelPackage).append(simpleClassName);
                }
                if (i > 0) {
                    stringBuilder.append(">");
                }
            }
            return stringBuilder.toString();
        }
        return null;
    }

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

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }
}
