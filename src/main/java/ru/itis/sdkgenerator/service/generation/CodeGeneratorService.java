package ru.itis.sdkgenerator.service.generation;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CodeGeneratorService {
    private final Configuration configuration;

    @Autowired
    public CodeGeneratorService(@Qualifier("javaClassFreeMarkerConfigurationFactoryBean") Configuration configuration) {
        this.configuration = configuration;
    }

    public void renderPom(){
        try {
            Template template = configuration.getTemplate("/pom.ftlh");

        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }
}
