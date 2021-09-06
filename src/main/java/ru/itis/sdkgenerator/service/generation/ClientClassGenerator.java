package ru.itis.sdkgenerator.service.generation;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.sdkgenerator.data.ClientInfo;
import ru.itis.sdkgenerator.properties.SdkGeneratorProperties;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ClientClassGenerator {
    private final SdkGeneratorProperties sdkGeneratorProperties;
    private final Configuration configuration;

    @Autowired
    public ClientClassGenerator(SdkGeneratorProperties sdkGeneratorProperties, @Qualifier("freemarkerConfiguration") Configuration configuration) {
        this.sdkGeneratorProperties = sdkGeneratorProperties;
        this.configuration = configuration;
    }


    public void generateClientClass(File basePackageDirectory, ClientInfo clientInfo) {
        try {
            String clientName = sdkGeneratorProperties.getClientName() != null ?
                    sdkGeneratorProperties.getClientName() : "Rest";
            File file = new File(basePackageDirectory, clientName + "Client.java");
            file.createNewFile();
            Template template = configuration.getTemplate("/ClientClass.ftlh");

            Map<String, Object> data = new HashMap<>();
            data.put("package", sdkGeneratorProperties.getBasePackage());
            if (sdkGeneratorProperties.getBaseUrl() == null) {
                throw new IllegalStateException("baseUrl property is not defined");
            }
            data.put("baseUrl", sdkGeneratorProperties.getBaseUrl());
            data.put("serviceName", clientName);
            data.put("authenticationMethod", clientInfo.getAuthenticationMethod());
            data.put("services", clientInfo.getServices());

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            template.process(data, bufferedWriter);
            bufferedWriter.close();

        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }

    }
}
