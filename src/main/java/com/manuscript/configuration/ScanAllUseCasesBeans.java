package com.manuscript.configuration;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan
public class ScanAllUseCasesBeans {

    @Bean
    BeanFactoryPostProcessor beanFactoryPostProcessor(ApplicationContext beanRegistry) {
        return beanFactory -> {
                genericApplicationContext(beanRegistry);
        };
    }

    void genericApplicationContext(ApplicationContext beanRegistry) {
        ClassPathBeanDefinitionScanner beanDefinitionScanner = new ClassPathBeanDefinitionScanner((BeanDefinitionRegistry) beanRegistry);
        beanDefinitionScanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        beanDefinitionScanner.scan("com.manuscript.core.usecase.custom");
        beanDefinitionScanner.scan("com.manuscript.rest.service");
        beanDefinitionScanner.scan("com.manuscript.persistence.sql.service");
        beanDefinitionScanner.scan("com.manuscript.persistence.nosql.service");
        beanDefinitionScanner.scan("com.manuscript.infrastructure");
    }


}
