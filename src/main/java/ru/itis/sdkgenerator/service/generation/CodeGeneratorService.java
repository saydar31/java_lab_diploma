package ru.itis.sdkgenerator.service.generation;

import org.springframework.stereotype.Component;
import ru.itis.sdkgenerator.data.ClientInfo;

@Component
public class CodeGeneratorService {

    private final PomGenerator pomGenerator;
    private final ServiceClassGenerator serviceClassGenerator;
    private final DataClassesGenerator dataClassesGenerator;

    public CodeGeneratorService(PomGenerator pomGenerator, ServiceClassGenerator serviceClassGenerator, DataClassesGenerator dataClassesGenerator) {
        this.pomGenerator = pomGenerator;
        this.serviceClassGenerator = serviceClassGenerator;
        this.dataClassesGenerator = dataClassesGenerator;
    }

    public void generateCode(ClientInfo clientInfo) {
        pomGenerator.renderPom();
        serviceClassGenerator.createServiceClasses(clientInfo);
        dataClassesGenerator.generateModelClasses(clientInfo);
    }
}
