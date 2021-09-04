package ru.itis.sdkgenerator.service.generation;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.sdkgenerator.data.ClientInfo;
import ru.itis.sdkgenerator.data.MethodParameter;
import ru.itis.sdkgenerator.properties.SdkGeneratorProperties;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DataClassesGenerator {
    private final Configuration configuration;

    private final SdkGeneratorProperties sdkGeneratorProperties;

    @Autowired
    public DataClassesGenerator(@Qualifier("freemarkerConfiguration") Configuration configuration, SdkGeneratorProperties sdkGeneratorProperties) {
        this.configuration = configuration;
        this.sdkGeneratorProperties = sdkGeneratorProperties;
    }

    public void generateModelClasses(ClientInfo clientInfo) {
        Set<Type> allTypes = clientInfo.getServices().stream()
                .flatMap(serviceInfo -> serviceInfo.getMethods().stream())
                .map(methodInfo -> {
                    Set<Type> types = methodInfo.getParameters().stream()
                            .map(MethodParameter::getType)
                            .collect(Collectors.toSet());
                    types.add(methodInfo.getResponseBodyType());
                    return types;
                })
                .flatMap(Collection::stream)
                .filter(type -> {
                    if (type instanceof Class) {
                        return ((Class<?>) type).getName().startsWith("java");
                    }
                    return true;
                })
                .collect(Collectors.toSet());


    }

    private void prepareDirectories(){

    }

    private void generateClass(Class<?> clazz){

    }

}
