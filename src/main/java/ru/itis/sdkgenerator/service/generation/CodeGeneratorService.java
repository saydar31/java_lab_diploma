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

    public CodeGeneratorService(PomGenerator pomGenerator, ServiceClassGenerator serviceClassGenerator, DataClassesGenerator dataClassesGenerator, DirectoriesCreator directoriesCreator) {
        this.pomGenerator = pomGenerator;
        this.serviceClassGenerator = serviceClassGenerator;
        this.dataClassesGenerator = dataClassesGenerator;
        this.directoriesCreator = directoriesCreator;
    }

    public void generateCode(ClientInfo clientInfo) {
        SdkProjectStructure projectDirectories = directoriesCreator.createProjectDirectories();
        pomGenerator.renderPom(projectDirectories.getRootDirectory());
        serviceClassGenerator.createServiceClasses(projectDirectories.getServicesDirectory(), clientInfo);
        dataClassesGenerator.generateModelClasses(projectDirectories.getDataDirectory(), clientInfo);
    }
}
