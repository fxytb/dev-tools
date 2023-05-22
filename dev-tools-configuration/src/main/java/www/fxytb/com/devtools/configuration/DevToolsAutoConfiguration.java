package www.fxytb.com.devtools.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import www.fxytb.com.devtools.config.properties.DevToolsProperties;

@ComponentScan("www.fxytb.com.devtools")
@EnableConfigurationProperties(value = DevToolsProperties.class)
public class DevToolsAutoConfiguration {}
