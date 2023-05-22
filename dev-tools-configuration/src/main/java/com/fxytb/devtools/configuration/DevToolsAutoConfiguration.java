package com.fxytb.devtools.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import www.com.devtools.config.properties.DevToolsProperties;

@ComponentScan("com.fxytb.devtools")
@EnableConfigurationProperties(value = DevToolsProperties.class)
public class DevToolsAutoConfiguration {}
