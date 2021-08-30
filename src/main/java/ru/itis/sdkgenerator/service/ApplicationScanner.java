package ru.itis.sdkgenerator.service;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.sdkgenerator.annotations.SdkController;
import ru.itis.sdkgenerator.annotations.SdkMethod;
import ru.itis.sdkgenerator.data.MethodInfo;
import ru.itis.sdkgenerator.data.ServiceInfo;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Map;

@Service
public class ApplicationScanner {

    @Value("${sdk.enabled}")
    private boolean sdkIsOn;

    @Autowired
    private ListableBeanFactory listableBeanFactory;
    @Autowired
    private ControllerScanner controllerScanner;

    @PostConstruct
    public void build(){
        Map<String, Object> sdkControllers = listableBeanFactory.getBeansWithAnnotation(SdkController.class);
        /*
        * получаем список методов помеченных аннотацией
        * из метода нужно получить
        * url(доменное имя + request mapping на классе + request mapping на методе)
        * http метод
        * класс внутри response entry
        * из аннотации получить имя метода
        */
        for (Map.Entry<String, Object> stringObjectEntry : sdkControllers.entrySet()) {
            Object controller = stringObjectEntry.getValue();
            ServiceInfo serviceInfo = controllerScanner.scan(controller);
        }

    }

}
