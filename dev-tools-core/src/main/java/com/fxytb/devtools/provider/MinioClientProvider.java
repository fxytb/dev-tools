package com.fxytb.devtools.provider;

import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import www.com.devtools.config.properties.DevToolsProperties;

@Component
public class MinioClientProvider {

    @Bean
    @ConditionalOnProperty(name = "dev-tools.minio.enable", havingValue = "true", matchIfMissing = true)
    public MinioClient provideMinioClient(DevToolsProperties devToolsProperties) {
        DevToolsProperties.Minio minio = devToolsProperties.getMinio();
        String endpoint = minio.getEndpoint();
        Integer port = minio.getPort();
        String accessKey = minio.getAccessKey();
        String secretKey = minio.getSecretKey();
        return MinioClient.builder().endpoint(endpoint, port, false).credentials(accessKey, secretKey).build();
    }


}
