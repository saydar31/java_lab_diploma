package ru.itis.sdkgenerator;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.itis.sdkgenerator.properties.SdkGeneratorProperties;

@Configuration
@ComponentScan
@EnableConfigurationProperties(SdkGeneratorProperties.class)
public class AutoConfigurator {
}
