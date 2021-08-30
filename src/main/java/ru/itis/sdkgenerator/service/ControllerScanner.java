package ru.itis.sdkgenerator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.sdkgenerator.annotations.SdkMethod;
import ru.itis.sdkgenerator.data.MethodInfo;
import ru.itis.sdkgenerator.data.ServiceInfo;

import java.lang.reflect.Method;

@Component
public class ControllerScanner {
    @Autowired
    private MethodScanner methodScanner;

    public ServiceInfo scan(Object controller) {
        ServiceInfo serviceInfo = new ServiceInfo();
        Class<?> controllerClass = controller.getClass();
        String beanUrlPrefix = "";
        if (controllerClass.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping requestMapping = controllerClass.getAnnotation(RequestMapping.class);
            String[] paths = requestMapping.value();
            if (paths.length > 0) {
                beanUrlPrefix = paths[0];
            }
        }
        for (Method method : controllerClass.getMethods()) {
            if (method.isAnnotationPresent(SdkMethod.class)) {
                MethodInfo methodInfo = methodScanner.scan(method);
            }
        }
        return serviceInfo;
    }
}
