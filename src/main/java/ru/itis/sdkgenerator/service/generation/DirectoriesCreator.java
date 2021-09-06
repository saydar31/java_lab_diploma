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

        File javaCodeDirectory = new File(rootDirectory, "src" + File.separator + "main" + File.separator + "java");
        javaCodeDirectory.mkdirs();

        //base package
        String basePackage = sdkGeneratorProperties.getBasePackage() + ".rest.client";
        File basePackageDirectory = new File(javaCodeDirectory, basePackage.replace(".", File.separator));
        basePackageDirectory.mkdirs();

        //services package
        File servicesDirectory = new File(basePackageDirectory, "services");
        servicesDirectory.mkdirs();

        //model path
        File modelDirectory = new File(basePackageDirectory, "model");
        modelDirectory.mkdirs();

        return new SdkProjectStructure(rootDirectory, basePackageDirectory, servicesDirectory, modelDirectory);
    }
}
