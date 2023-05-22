package com.fxytb.devtools.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "dev-tools")
public class DevToolsProperties {

    private Minio minio = new Minio();

    @Data
    public static class Minio {
        private Boolean enable;
        private String endpoint;
        private Integer port;
        private String accessKey;
        private String secretKey;
    }

}
