package ru.itis.sdkgenerator.service;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.itis.sdkgenerator.data.ClientInfo;
import ru.itis.sdkgenerator.service.generation.CodeGeneratorService;

@Component
public class SdkGenerator {
    private final ApplicationScanner applicationScanner;
    private final CodeGeneratorService codeGeneratorService;

    public SdkGenerator(ApplicationScanner applicationScanner, CodeGeneratorService codeGeneratorService) {
        this.applicationScanner = applicationScanner;
        this.codeGeneratorService = codeGeneratorService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void generate(){
        ClientInfo clientInfo = applicationScanner.scan();
        codeGeneratorService.generateCode(clientInfo);
    }
}
