package ru.itis.sdkgenerator.data;

import java.util.List;

public class ServiceInfo {
    private String name;
    private List<MethodInfo> methods;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MethodInfo> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodInfo> methods) {
        this.methods = methods;
    }
}
