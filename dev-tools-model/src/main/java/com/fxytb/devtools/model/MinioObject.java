package com.fxytb.devtools.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.InputStream;

@Data
@Accessors(chain = true)
public class MinioObject {

    private static MinioObject minioObjectLazy;

    private static final MinioObject minioObjectHunger = new MinioObject();

    private MinioObject() {
    }

    private String objectName;
    private String bucketName;
    private String sourceObjectName;
    private String sourceBucketName;
    private String fileName;
    private String contentType;
    private InputStream inputStream;
    private Long objectSize;
    private Long partSize;

    public static synchronized MinioObject minioObjectBuilderLazy() {
        if (minioObjectLazy == null) {
            return new MinioObject();
        }
        return minioObjectLazy;
    }

    public static MinioObject minioObjectBuilderHunger() {
        return minioObjectHunger;
    }
}
