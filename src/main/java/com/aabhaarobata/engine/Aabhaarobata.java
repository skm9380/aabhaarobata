package com.aabhaarobata.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableAutoConfiguration
@EntityScan({ "sample.jpa.models", "com.aabhaarobata" })
@ComponentScan(basePackages = { "com.aabhaarobata" })
public class Aabhaarobata extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Aabhaarobata.class);
    }	
	
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Aabhaarobata.class, args);
    }
    
/*    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000);
        return new CommonsMultipartResolver();
    }  */ 
    
}