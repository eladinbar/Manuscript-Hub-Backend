package com.manuscript.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {


    @Bean(name = "web-api")
    public static String getWebApi() {
        if (System.getenv("WEB_API") == null) {
            String url = "http://localhost:8080";
            System.err.println("WEB_API for local usage going to use public ip !!! ");
            return url;
        }
        String url = "http://" + System.getenv("WEB_API");
        return url;
    }

//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setDefaultEncoding("utf-8");
//        return resolver;
//    }
}
