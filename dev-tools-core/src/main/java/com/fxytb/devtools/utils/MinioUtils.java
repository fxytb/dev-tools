package com.fxytb.devtools.utils;

import com.fxytb.devtools.model.MinioObject;
import com.google.common.collect.Lists;
import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.fxytb.devtools.enums.MinioOperationStatusEnum.*;


@Slf4j
public class MinioUtils {

    /**
     * 获取所有Minio存储桶的列表。
     *
     * @param minioClient MinioClient实例
     * @return 表示Minio存储桶的Bucket对象列表
     */
    public static List<Bucket> listBuckets(MinioClient minioClient) {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            handleException(BUCKETS_QUERY_ENUM.getExceptionMessage(), e);
        }
        return Lists.newArrayList();
    }

    /**
     * 检查Minio中是否存在存储桶。
     *
     * @param minioClient MinioClient实例
     * @param bucketName  要检查的存储桶名称
     * @return 如果存储桶存在则返回1，如果不存在则返回0，如果发生异常则返回-1
     */
    public static Integer bucketIsExists(MinioClient minioClient, String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build()) ? BUCKET_IS_EXISTS_ENUM.getSuccessFlag() : BUCKET_IS_EXISTS_ENUM.getFailFlag();
        } catch (Exception e) {
            handleException(BUCKET_IS_EXISTS_ENUM.getExceptionMessage(), e);
            return BUCKET_IS_EXISTS_ENUM.getExceptionFlag();
        }
    }

    /**
     * 创建Minio存储桶。
     *
     * @param minioClient MinioClient实例
     * @param bucketName  要创建的存储桶名称
     * @return 如果存储桶已成功创建，则返回1；如果存储桶已存在，则返回0；如果发生异常，则返回-1
     */
    public static Integer makeBucket(MinioClient minioClient, String bucketName) {
        Integer flag = bucketIsExists(minioClient, bucketName);
        if (0 == flag) {
            try {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                return BUCKET_MAKE_ENUM.getSuccessFlag();
            } catch (Exception e) {
                handleException(BUCKET_MAKE_ENUM.getExceptionMessage(), e);
                return BUCKET_MAKE_ENUM.getExceptionFlag();
            }
        }
        return BUCKET_MAKE_ENUM.getFailFlag();
    }

    /**
     * 删除Minio存储桶。
     *
     * @param minioClient MinioClient实例
     * @param bucketName  要删除的存储桶名称
     * @return 如果存储桶成功删除，则返回1；如果存储桶不存在，则返回0；如果发生异常，则返回-1
     */
    public static Integer removeBucket(MinioClient minioClient, String bucketName) {
        Integer flag = bucketIsExists(minioClient, bucketName);
        if (BUCKET_IS_EXISTS_ENUM.getSuccessFlag().equals(flag)) {
            try {
                minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
                return BUCKET_REMOVE_ENUM.getSuccessFlag();
            } catch (Exception e) {
                log.error(BUCKET_REMOVE_ENUM.getExceptionMessage(), e);
                return BUCKET_REMOVE_ENUM.getExceptionFlag();
            }
        }
        return BUCKET_REMOVE_ENUM.getFailFlag();
    }

    /**
     * 获取指定存储桶中的对象列表。
     *
     * @param minioClient Minio客户端对象
     * @param bucketName  存储桶名称
     * @return 存储桶中的对象列表
     */
    public static List<Item> listObjects(MinioClient minioClient, String bucketName) {
        Integer flag = bucketIsExists(minioClient, bucketName);
        List<Item> list = new ArrayList<>();
        if (BUCKET_IS_EXISTS_ENUM.getSuccessFlag().equals(flag)) {
            for (Result<Item> result : minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build())) {
                try {
                    Item item = result.get();
                    list.add(item);
                } catch (Exception e) {
                    handleException(OBJECTS_QUERY_ENUM.getExceptionMessage(), e);
                }
            }
        }
        return list;
    }

    /**
     * 将对象上传到Minio存储桶。
     *
     * @param minioClient MinioClient实例
     * @param minioObject MinioObject对象，包含上传的对象信息
     * @return 操作结果：1表示成功，-1表示失败，0表示存储桶不存在
     */
    public static Integer putObject(MinioClient minioClient, MinioObject minioObject) {
        Integer flag = bucketIsExists(minioClient, minioObject.getBucketName());
        if (BUCKET_IS_EXISTS_ENUM.getSuccessFlag().equals(flag)) {
            try {
                minioClient.putObject(PutObjectArgs.builder().bucket(minioObject.getBucketName()).object(minioObject.getObjectName()).contentType(minioObject.getContentType()).stream(minioObject.getInputStream(), minioObject.getObjectSize(), minioObject.getPartSize()).build());
                return OBJECT_PUT_ENUM.getSuccessFlag();
            } catch (Exception e) {
                handleException(OBJECT_PUT_ENUM.getExceptionMessage(), e);
                return OBJECT_PUT_ENUM.getExceptionFlag();
            }
        }
        return OBJECT_PUT_ENUM.getFailFlag();
    }

    /**
     * 从Minio存储桶中删除对象。
     *
     * @param minioClient MinioClient实例
     * @param minioObject MinioObject对象，包含删除的对象信息
     * @return 操作结果：1表示成功，-1表示失败，0表示存储桶不存在
     */
    public static Integer removeObject(MinioClient minioClient, MinioObject minioObject) {
        Integer flag = bucketIsExists(minioClient, minioObject.getBucketName());
        if (BUCKET_IS_EXISTS_ENUM.getSuccessFlag().equals(flag)) {
            try {
                minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioObject.getBucketName()).object(minioObject.getObjectName()).build());
                return OBJECT_REMOVE_ENUM.getSuccessFlag();
            } catch (Exception e) {
                handleException(OBJECT_REMOVE_ENUM.getExceptionMessage(), e);
                return OBJECT_REMOVE_ENUM.getExceptionFlag();
            }
        }
        return OBJECT_REMOVE_ENUM.getFailFlag();
    }

    /**
     * 将本地文件上传到Minio存储桶。
     *
     * @param minioClient MinioClient实例
     * @param minioObject MinioObject对象，包含上传的对象信息
     * @return 操作结果：1表示成功，-1表示失败，0表示存储桶不存在
     */
    public static Integer uploadObject(MinioClient minioClient, MinioObject minioObject) {
        Integer flag = bucketIsExists(minioClient, minioObject.getBucketName());
        if (BUCKET_IS_EXISTS_ENUM.getSuccessFlag().equals(flag)) {
            try {
                minioClient.uploadObject(UploadObjectArgs.builder().bucket(minioObject.getBucketName()).object(minioObject.getObjectName()).contentType(minioObject.getContentType()).filename(minioObject.getFileName()).build());
                return OBJECT_UPLOAD_ENUM.getSuccessFlag();
            } catch (Exception e) {
                handleException(OBJECT_UPLOAD_ENUM.getExceptionMessage(), e);
                return OBJECT_UPLOAD_ENUM.getExceptionFlag();
            }
        }
        return OBJECT_UPLOAD_ENUM.getFailFlag();
    }

    /**
     * 从Minio存储桶中下载对象到本地文件。
     *
     * @param minioClient MinioClient实例
     * @param minioObject MinioObject对象，包含下载的对象信息
     * @return 操作结果：1表示成功，-1表示失败，0表示存储桶不存在
     */
    public static Integer downloadObject(MinioClient minioClient, MinioObject minioObject) {
        Integer flag = bucketIsExists(minioClient, minioObject.getBucketName());
        if (BUCKET_IS_EXISTS_ENUM.getSuccessFlag().equals(flag)) {
            try {
                minioClient.downloadObject(DownloadObjectArgs.builder().bucket(minioObject.getBucketName()).object(minioObject.getObjectName()).filename(minioObject.getFileName()).build());
                return OBJECT_DOWNLOAD_ENUM.getSuccessFlag();
            } catch (Exception e) {
                handleException(OBJECT_DOWNLOAD_ENUM.getExceptionMessage(), e);
                return OBJECT_DOWNLOAD_ENUM.getExceptionFlag();
            }
        }
        return OBJECT_DOWNLOAD_ENUM.getFailFlag();
    }

    /**
     * 获取Minio存储桶中的对象流。
     *
     * @param minioClient MinioClient实例
     * @param minioObject MinioObject对象，包含获取的对象信息
     * @return 对象的InputStream流，如果操作失败则返回null
     */
    public static InputStream getObject(MinioClient minioClient, MinioObject minioObject) {
        Integer flag = bucketIsExists(minioClient, minioObject.getBucketName());
        if (BUCKET_IS_EXISTS_ENUM.getSuccessFlag().equals(flag)) {
            try {
                return minioClient.getObject(GetObjectArgs.builder().bucket(minioObject.getBucketName()).object(minioObject.getObjectName()).build());
            } catch (Exception e) {
                handleException(OBJECT_GET_ENUM.getExceptionMessage(), e);
            }
        }
        return null;
    }

    /**
     * 将Minio存储桶中的对象复制到另一个存储桶。
     *
     * @param minioClient MinioClient实例
     * @param minioObject MinioObject对象，包含复制的对象信息
     * @return 操作结果：1表示成功，-1表示失败，0表示存储桶不存在
     */
    public static Integer copyObject(MinioClient minioClient, MinioObject minioObject) {
        Integer flag = bucketIsExists(minioClient, minioObject.getBucketName());
        if (BUCKET_IS_EXISTS_ENUM.getSuccessFlag().equals(flag)) {
            try {
                minioClient.copyObject(CopyObjectArgs.builder().bucket(minioObject.getBucketName()).object(minioObject.getObjectName()).source(CopySource.builder().bucket(minioObject.getSourceBucketName()).object(minioObject.getSourceObjectName()).build()).build());
                return OBJECT_COPY_ENUM.getSuccessFlag();
            } catch (Exception e) {
                handleException(OBJECT_COPY_ENUM.getExceptionMessage(), e);
                return OBJECT_COPY_ENUM.getExceptionFlag();
            }
        }
        return OBJECT_GET_ENUM.getFailFlag();
    }

    /**
     * 获取InputStream流的内容类型。
     *
     * @param inputStream InputStream流
     * @return 内容类型
     * @throws IOException 如果无法读取内容类型
     */
    public static String getContentType(InputStream inputStream) throws IOException {
        return new Tika().detect(inputStream);
    }

    /**
     * 获取本地文件的内容类型。
     *
     * @param file 本地文件
     * @return 内容类型
     * @throws IOException 如果无法读取内容类型
     */
    public static String getContentType(File file) throws IOException {
        return new Tika().detect(file);
    }


    /**
     * 处理Minio相关操作可能抛出的异常。
     *
     * @param errorMessage 异常信息
     * @param exception    异常对象
     */
    private static void handleException(String errorMessage, Exception exception) {
        log.error(errorMessage, exception);
    }

}
