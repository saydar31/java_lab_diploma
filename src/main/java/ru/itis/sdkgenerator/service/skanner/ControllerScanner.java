package ru.itis.sdkgenerator.service.skanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.sdkgenerator.annotations.SdkController;
import ru.itis.sdkgenerator.annotations.SdkMethod;
import ru.itis.sdkgenerator.data.MethodInfo;
import ru.itis.sdkgenerator.data.ServiceInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class ControllerScanner {
    @Autowired
    private MethodScanner methodScanner;

    public ServiceInfo scan(Class<?> controllerClass) {
        ServiceInfo serviceInfo = new ServiceInfo();
        String beanUrlPrefix = "";
        if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = controllerClass.getAnnotation(RequestMapping.class);
            String[] paths = requestMapping.value();
            if (paths.length > 0) {
                beanUrlPrefix = paths[0];
            }
        }
        List<MethodInfo> methods = new ArrayList<>();
        for (Method method : controllerClass.getMethods()) {
            if (method.isAnnotationPresent(SdkMethod.class)) {
                MethodInfo methodInfo = methodScanner.scan(method, beanUrlPrefix);
                methods.add(methodInfo);
            }
        }
        serviceInfo.setMethods(methods);
        serviceInfo.setName(controllerClass.getAnnotation(SdkController.class).serviceName());
        return serviceInfo;
    }
}
