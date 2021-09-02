package ru.itis.sdkgenerator.service.generation;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.sdkgenerator.data.ClientInfo;
import ru.itis.sdkgenerator.properties.SdkGeneratorProperties;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class PomGenerator {
    private final Configuration configuration;

    private final SdkGeneratorProperties sdkGeneratorProperties;

    @Autowired
    public PomGenerator(@Qualifier("freemarkerConfiguration") Configuration configuration, SdkGeneratorProperties sdkGeneratorProperties) {
        this.configuration = configuration;
        this.sdkGeneratorProperties = sdkGeneratorProperties;
    }

    public void renderPom() {
        try {
            Template template = configuration.getTemplate("/pom.ftlh");
            String sdkOutPath = sdkGeneratorProperties.getOutputPath() != null ?
                    sdkGeneratorProperties.getOutputPath() : "." + File.separator + "sdk-out";
            File pomXml = new File(sdkOutPath + File.separator + "pom.xml");
            pomXml.createNewFile();
            Writer writer = new BufferedWriter(new FileWriter(pomXml));
            Map<String, Object> model = new HashMap<>();
            model.put("groupId",
                    sdkGeneratorProperties.getGroupId() != null ?
                            sdkGeneratorProperties.getGroupId() : "com.company");
            model.put("artifactId",
                    sdkGeneratorProperties.getGroupId() != null ?
                            sdkGeneratorProperties.getArtifactId() : "demo");
            model.put("version",
                    sdkGeneratorProperties.getVersion() != null ?
                            sdkGeneratorProperties.getArtifactId() : "1.0-SNAPSHOT");
            template.process(model, writer);
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
    }
}
