package ru.itis.sdkgenerator.config;

import freemarker.template.Version;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FreemarkerConfig {

    @Bean
    public freemarker.template.Configuration freemarkerConfiguration() {
        freemarker.template.Configuration conf = new freemarker.template.Configuration(new Version(2, 3, 29));
        conf.setClassForTemplateLoading(FreemarkerConfig.class, "/templates/");
        return conf;
    }
}
