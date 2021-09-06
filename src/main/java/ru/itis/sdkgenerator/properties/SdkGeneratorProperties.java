package ru.itis.sdkgenerator.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sdk-generator")
public class SdkGeneratorProperties {
    private String baseUrl;
    private String basePackage;
    private String groupId;
    private String artifactId;
    private String clientName;
    private String outputPath;
    private String version;
    private Class<?> errorBodyClass;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBasePackage() {
        if (basePackage != null){
            return basePackage;
        } else {
            return "com.company.demo";
        }
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Class<?> getErrorBodyClass() {
        return errorBodyClass;
    }

    public String getErrorBodyClassName() {
        return errorBodyClass.getName();
    }

    public void setErrorBodyClass(Class<?> errorBodyClass) {
        this.errorBodyClass = errorBodyClass;
    }
}
