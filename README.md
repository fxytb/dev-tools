# dev-tools
开发工具包

1. 新增minio工具类,配置如下
```yml
dev-tools:
  minio:
    enable: true/false true会加载配置注入minioClient,false则不会
    endpoint: minio地址
    port: minio端口
    accessKey: minio accessKey
    secretKey: minio secretKey
```
依赖如下
```xml
<dependency>
    <groupId>com.fxytb</groupId>
    <artifactId>dev-tools-core</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.fxytb</groupId>
    <artifactId>dev-tools-configuration</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.fxytb</groupId>
    <artifactId>dev-tools-model</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```
