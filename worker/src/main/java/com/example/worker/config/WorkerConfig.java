package com.example.worker.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySource("classpath:application.properties")
public class WorkerConfig extends WebMvcConfigurerAdapter {
    private final Logger logger = Logger.getLogger(this.getClass());

}