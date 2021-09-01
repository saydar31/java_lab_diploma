package ru.itis.sdkgenerator.service;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.sdkgenerator.annotations.SdkController;
import ru.itis.sdkgenerator.annotations.SdkMethod;
import ru.itis.sdkgenerator.data.ClientInfo;
import ru.itis.sdkgenerator.data.MethodInfo;
import ru.itis.sdkgenerator.data.ServiceInfo;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationScanner {

    @Value("${sdk.enabled}")
    private boolean sdkIsOn;

    @Autowired
    private ListableBeanFactory listableBeanFactory;
    @Autowired
    private ControllerScanner controllerScanner;

    public ClientInfo getClientInfo() {
        Map<String, Object> sdkControllers = listableBeanFactory.getBeansWithAnnotation(SdkController.class);
        /*
         * получаем список методов помеченных аннотацией
         * из метода нужно получить
         * url(доменное имя + request mapping на классе + request mapping на методе)
         * http метод
         * класс внутри response entry
         * из аннотации получить имя метода
         */
        List<ServiceInfo> services = new ArrayList<>();
        for (Map.Entry<String, Object> stringObjectEntry : sdkControllers.entrySet()) {
            Object controller = stringObjectEntry.getValue();
            ServiceInfo serviceInfo = controllerScanner.scan(controller);
            services.add(serviceInfo);
        }
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setServiceName("Rest");
        clientInfo.setBasePackage("ru.itis");
        clientInfo.setServices(services);
        return clientInfo;
    }
}
