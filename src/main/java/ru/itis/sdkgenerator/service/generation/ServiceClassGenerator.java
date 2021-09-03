package ru.itis.sdkgenerator.service.generation;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.sdkgenerator.data.ClientInfo;
import ru.itis.sdkgenerator.data.ServiceInfo;
import ru.itis.sdkgenerator.properties.SdkGeneratorProperties;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public void createServiceClasses(ClientInfo clientInfo) {
        String sdkOutPath = sdkGeneratorProperties.getOutputPath() != null ?
                sdkGeneratorProperties.getOutputPath() : "." + File.separator + "sdk-out";
        String basePackage = sdkGeneratorProperties.getBasePackage() != null ?
                sdkGeneratorProperties.getBasePackage() : "com.company.demo";
        String sourceCodePath = sdkOutPath + File.separator + "src" + File.separator + "main" + File.separator + "java";

        if (sdkGeneratorProperties.getErrorBodyClass() == null){
            throw new IllegalStateException();
        }
        String errorBodyType = basePackage + ".model." + sdkGeneratorProperties.getErrorBodyClass().getSimpleName();

        String servicesFolderPath = getServicesFolderPath(sourceCodePath, basePackage + ".rest.client.services");
        File servicesFolder = new File(servicesFolderPath);
        servicesFolder.mkdirs();

        for (ServiceInfo service : clientInfo.getServices()) {
            try {
                Map<String, Object> data = new HashMap<>();
                data.put("package", basePackage);
                data.put("name", service.getName());
                data.put("appName", clientInfo.getServiceName());
                data.put("methods", service.getMethods());
                data.put("requestErrorClassName", errorBodyType);

                Template template = configuration.getTemplate("/ServiceClass.ftlh");

                File classFile = new File(getClassPath(basePackage, service.getName() + "Service.java", sourceCodePath));
                classFile.createNewFile();

                template.process(data, new BufferedWriter(new FileWriter(classFile)));

            } catch (IOException | TemplateException e) {
                throw new IllegalStateException(e);
            }
        }

    }

    private String getClassPath(String basePackage, String className, String sourceCodePath) {
        String fullPackage = basePackage + ".rest.client.services";
        String packageAsPathPart = fullPackage.replace(".", File.separator);
        return sourceCodePath + File.separator + packageAsPathPart + File.separator + className;
    }

    private String getServicesFolderPath(String sourceCodePath, String servicesPackage) {
        return sourceCodePath + File.separator + servicesPackage.replace(".", File.separator);
    }

}
