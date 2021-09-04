package ru.itis.sdkgenerator.data;

import java.io.File;

public class SdkProjectStructure {
    private final File rootDirectory;
    private final File basePackageDirectory;
    private final File servicesDirectory;
    private final File dataDirectory;

    public SdkProjectStructure(File rootDirectory, File basePackageDirectory, File servicesDirectory, File dataDirectory) {
        this.rootDirectory = rootDirectory;
        this.basePackageDirectory = basePackageDirectory;
        this.servicesDirectory = servicesDirectory;
        this.dataDirectory = dataDirectory;
    }

    public File getRootDirectory() {
        return rootDirectory;
    }

    public File getBasePackageDirectory() {
        return basePackageDirectory;
    }

    public File getServicesDirectory() {
        return servicesDirectory;
    }

    public File getDataDirectory() {
        return dataDirectory;
    }
}
