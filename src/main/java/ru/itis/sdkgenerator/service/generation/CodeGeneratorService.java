package ru.itis.sdkgenerator.service.generation;

import org.springframework.stereotype.Component;

@Component
public class CodeGeneratorService {

    private final PomGenerator pomGenerator;

    public CodeGeneratorService(PomGenerator pomGenerator) {
        this.pomGenerator = pomGenerator;
    }

    public void generateCode() {
        pomGenerator.renderPom();
    }
}
