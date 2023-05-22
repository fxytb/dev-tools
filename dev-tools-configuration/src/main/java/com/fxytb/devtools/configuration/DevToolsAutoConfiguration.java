package com.fxytb.devtools.configuration;

import com.fxytb.devtools.config.properties.DevToolsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.fxytb.devtools")
@EnableConfigurationProperties(value = DevToolsProperties.class)
public class DevToolsAutoConfiguration {}
