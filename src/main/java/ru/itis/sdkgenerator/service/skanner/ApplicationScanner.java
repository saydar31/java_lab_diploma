package ru.itis.sdkgenerator.service.skanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import ru.itis.sdkgenerator.annotations.SdkController;
import ru.itis.sdkgenerator.data.ClientInfo;
import ru.itis.sdkgenerator.data.MethodInfo;
import ru.itis.sdkgenerator.data.ServiceInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        Set<? extends Class<?>> controllerClasses = applicationContext.getBeansWithAnnotation(SdkController.class).values().stream()
                .map(Object::getClass)
                .collect(Collectors.toSet());

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
