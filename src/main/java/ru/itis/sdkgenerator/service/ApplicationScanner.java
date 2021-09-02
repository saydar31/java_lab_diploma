package ru.itis.sdkgenerator.service;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import ru.itis.sdkgenerator.annotations.SdkController;
import ru.itis.sdkgenerator.data.ClientInfo;
import ru.itis.sdkgenerator.data.MethodInfo;
import ru.itis.sdkgenerator.data.ServiceInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ApplicationScanner {

    @Autowired
    private ControllerScanner controllerScanner;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    public ClientInfo scan() {
        /*
         * получаем список методов помеченных аннотацией
         * из метода нужно получить
         * url(доменное имя + request mapping на классе + request mapping на методе)
         * http метод
         * класс внутри response entry
         * из аннотации получить имя метода
         */
        List<? extends Class<?>> controllerClasses = handlerMapping.getHandlerMethods().values().stream()
                .map(HandlerMethod::getBeanType)
                .filter(aClass -> aClass.isAnnotationPresent(SdkController.class))
                .distinct()
                .collect(Collectors.toList());

        List<ServiceInfo> services = new ArrayList<>();
        for (Class<?> controllerClass : controllerClasses) {
            ServiceInfo serviceInfo = controllerScanner.scan(controllerClass);
            services.add(serviceInfo);
        }
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setServiceName("Rest");
        clientInfo.setBasePackage("ru.itis");
        clientInfo.setServices(services);

        Optional<MethodInfo> methodInfoOptional = clientInfo.getServices().stream()
                .flatMap(serviceInfo -> serviceInfo.getMethods().stream())
                .filter(MethodInfo::isAuthenticationMethod).findAny();
        methodInfoOptional.ifPresent(clientInfo::setAuthenticationMethod);
        return clientInfo;
    }

}
