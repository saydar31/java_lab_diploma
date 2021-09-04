package ru.itis.sdkgenerator.service.generation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.sdkgenerator.data.SdkProjectStructure;
import ru.itis.sdkgenerator.properties.SdkGeneratorProperties;

import java.io.File;

@Component
public class DirectoriesCreator {
    private final SdkGeneratorProperties sdkGeneratorProperties;

    @Autowired
    public DirectoriesCreator(SdkGeneratorProperties sdkGeneratorProperties) {
        this.sdkGeneratorProperties = sdkGeneratorProperties;
    }

    public SdkProjectStructure createProjectDirectories() {
        //root
        String sdkOutPath = sdkGeneratorProperties.getOutputPath() != null ?
                sdkGeneratorProperties.getOutputPath() : "." + File.separator + "sdk-out";
        File rootDirectory = new File(sdkOutPath);
        rootDirectory.mkdirs();

        //base package
        String basePackage = sdkGeneratorProperties.getBasePackage() != null ?
                sdkGeneratorProperties.getBasePackage() : "com.company.demo";
        String basePackagePath = sdkOutPath + File.separator + basePackage.replace(".", File.separator);
        File file = new File(basePackagePath);
    }
}
