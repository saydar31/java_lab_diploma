package ru.itis.sdkgenerator.service.generation;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.sdkgenerator.data.ClientInfo;
import ru.itis.sdkgenerator.data.MethodInfo;
import ru.itis.sdkgenerator.data.MethodParameter;
import ru.itis.sdkgenerator.data.ServiceInfo;
import ru.itis.sdkgenerator.properties.SdkGeneratorProperties;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceClassGenerator {
    private final Configuration configuration;

    private final SdkGeneratorProperties sdkGeneratorProperties;

    @Autowired
    public ServiceClassGenerator(@Qualifier("freemarkerConfiguration") Configuration configuration, SdkGeneratorProperties sdkGeneratorProperties) {
        this.configuration = configuration;
        this.sdkGeneratorProperties = sdkGeneratorProperties;
    }

    public void createServiceClasses(File servicesDirectory, ClientInfo clientInfo) {
        String basePackage = sdkGeneratorProperties.getBasePackage();

        if (sdkGeneratorProperties.getErrorBodyClass() == null) {
            throw new IllegalStateException();
        }
        String errorBodyType = basePackage + ".rest.client.model." + sdkGeneratorProperties.getErrorBodyClass().getSimpleName();

        String modelPackage = basePackage + ".rest.client.model";
        for (ServiceInfo service : clientInfo.getServices()) {
            try {
                for (MethodInfo method : service.getMethods()) {
                    for (MethodParameter parameter : method.getParameters()) {
                        parameter.setModelPackage(modelPackage);
                    }
                    method.setModelPackage(modelPackage);
                }

                Map<String, Object> data = new HashMap<>();
                data.put("package", basePackage);
                data.put("name", service.getName());
                data.put("appName", clientInfo.getServiceName());
                data.put("methods", service.getMethods());
                data.put("requestErrorClassName", errorBodyType);

                Template template = configuration.getTemplate("/ServiceClass.ftlh");

                File classFile = new File(servicesDirectory, service.getName() + "Service.java");
                classFile.createNewFile();
                BufferedWriter fileWriter = new BufferedWriter(new FileWriter(classFile));
                template.process(data, fileWriter);
                fileWriter.close();
            } catch (IOException | TemplateException e) {
                throw new IllegalStateException(e);
            }
        }

    }

}
