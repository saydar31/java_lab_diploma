package ru.itis.sdkgenerator.service.generation;

import org.springframework.stereotype.Component;
import ru.itis.sdkgenerator.data.ClientInfo;

@Component
public class CodeGeneratorService {

    private final PomGenerator pomGenerator;
    private final ServiceClassGenerator serviceClassGenerator;

    public CodeGeneratorService(PomGenerator pomGenerator, ServiceClassGenerator serviceClassGenerator) {
        this.pomGenerator = pomGenerator;
        this.serviceClassGenerator = serviceClassGenerator;
    }

    public void generateCode(ClientInfo clientInfo) {
        pomGenerator.renderPom();
        serviceClassGenerator.createServiceClasses(clientInfo);
    }
}
