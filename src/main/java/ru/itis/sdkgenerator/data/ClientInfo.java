package ru.itis.sdkgenerator.data;

import java.util.List;

public class ClientInfo {
    private String basePackage;
    private String serviceName;
    private MethodInfo authenticationMethod;
    private List<ServiceInfo> services;

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public MethodInfo getAuthenticationMethod() {
        return authenticationMethod;
    }

    public void setAuthenticationMethod(MethodInfo authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    public List<ServiceInfo> getServices() {
        return services;
    }

    public void setServices(List<ServiceInfo> services) {
        this.services = services;
    }
}
