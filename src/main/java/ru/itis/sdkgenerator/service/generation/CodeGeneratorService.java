package ru.itis.sdkgenerator.service.generation;

import org.springframework.stereotype.Component;
import ru.itis.sdkgenerator.data.ClientInfo;
import ru.itis.sdkgenerator.data.SdkProjectStructure;

@Component
public class CodeGeneratorService {

    private final PomGenerator pomGenerator;
    private final ServiceClassGenerator serviceClassGenerator;
    private final DataClassesGenerator dataClassesGenerator;
    private final DirectoriesCreator directoriesCreator;
    private final ClientClassGenerator clientClassGenerator;

    public CodeGeneratorService(PomGenerator pomGenerator, ServiceClassGenerator serviceClassGenerator, DataClassesGenerator dataClassesGenerator, DirectoriesCreator directoriesCreator, ClientClassGenerator clientClassGenerator) {
        this.pomGenerator = pomGenerator;
        this.serviceClassGenerator = serviceClassGenerator;
        this.dataClassesGenerator = dataClassesGenerator;
        this.directoriesCreator = directoriesCreator;
        this.clientClassGenerator = clientClassGenerator;
    }

    public void generateCode(ClientInfo clientInfo) {
        SdkProjectStructure projectDirectories = directoriesCreator.createProjectDirectories();
        clientClassGenerator.generateClientClass(projectDirectories.getBasePackageDirectory(), clientInfo);
        pomGenerator.renderPom(projectDirectories.getRootDirectory());
        serviceClassGenerator.createServiceClasses(projectDirectories.getServicesDirectory(), clientInfo);
        dataClassesGenerator.generateModelClasses(projectDirectories.getDataDirectory(), clientInfo);
    }
}
