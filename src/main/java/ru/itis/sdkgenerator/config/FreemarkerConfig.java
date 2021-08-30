package ru.itis.sdkgenerator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class FreemarkerConfig {
    @Bean
    public FreeMarkerConfigurationFactoryBean javaClassFreeMarkerConfigurationFactoryBean() {
        FreeMarkerConfigurationFactoryBean factoryBean = new FreeMarkerConfigurationFactoryBean();
        factoryBean.setTemplateLoaderPath("/template");
        return factoryBean;
    }
}
