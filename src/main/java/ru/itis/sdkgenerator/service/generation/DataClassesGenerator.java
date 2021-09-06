package ru.itis.sdkgenerator.service.generation;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.sdkgenerator.data.ClientInfo;
import ru.itis.sdkgenerator.data.MethodParameter;
import ru.itis.sdkgenerator.properties.SdkGeneratorProperties;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
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

    public void generateModelClasses(File dataDirectory, ClientInfo clientInfo) {
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
                        Class<?> clazz = (Class<?>) type;
                        //we should generate code only for non-built-in classes
                        return !(clazz.getName().startsWith("java.") || clazz.equals(void.class) || clazz.isPrimitive() || clazz.isArray());
                    }
                    return true;
                })
                .collect(Collectors.toSet());
        allTypes.add(sdkGeneratorProperties.getErrorBodyClass());
        Set<? extends Class<?>> classes = allTypes.stream()
                .filter(type -> type instanceof Class)
                .map(type -> (Class<?>) type)
                .collect(Collectors.toSet());
        for (Class<?> aClass : classes) {
            generateClass(aClass, dataDirectory);
        }

    }

    private void generateClass(Class<?> clazz, File directory) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            Map<String, Object> data = new HashMap<>();
            Set<PropertyDescriptor> properties = Arrays.stream(beanInfo.getPropertyDescriptors())
                    .filter(property -> !property.getName().equals("class"))
                    .collect(Collectors.toSet());
            data.put("properties", properties);
            String basePackage = sdkGeneratorProperties.getBasePackage();
            data.put("package", basePackage);
            data.put("className", clazz.getSimpleName());
            data.put("isErrorObject", sdkGeneratorProperties.getErrorBodyClass().equals(clazz));
            Template template = configuration.getTemplate("/objectClass.ftlh");
            File file = new File(directory, clazz.getSimpleName() + ".java");
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            template.process(data, bufferedWriter);
            bufferedWriter.close();
        } catch (IntrospectionException | IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
    }

}
