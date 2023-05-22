package www.fxytb.com.devtools.enums;

import lombok.Getter;

@Getter
public enum MinioOperationStatusEnum {
    BUCKETS_QUERY_ENUM("存储桶列表查询成功", "存储桶列表为空", "存储桶列表查询异常"),
    BUCKET_IS_EXISTS_ENUM("存储桶存在", "存储桶不存在", "查询存储桶存在异常"),
    BUCKET_MAKE_ENUM("存储桶创建成功", "存储桶创建失败", "存储桶创建异常"),
    BUCKET_REMOVE_ENUM("存储桶移除成功", "存储桶移除失败", "存储桶移除异常"),
    OBJECTS_QUERY_ENUM("对象列表查询成功", "对象列表为空", "对象列表查询异常"),
    OBJECT_PUT_ENUM("对象更新成功", "对象更新失败", "对象更新异常"),
    OBJECT_REMOVE_ENUM("对象移除成功", "对象移除失败", "对象移除异常"),
    OBJECT_UPLOAD_ENUM("对象上传成功", "对象上传失败", "对象上传异常"),
    OBJECT_DOWNLOAD_ENUM("对象下载成功", "对象下载失败", "对象下载异常"),
    OBJECT_GET_ENUM("对象获取成功", "对象获取失败", "对象获取异常"),
    OBJECT_COPY_ENUM("对象复制成功", "对象复制失败", "对象复制异常"),
    ;
    private final String successMessage;
    private final String failMessage;
    private final String exceptionMessage;
    private final Integer successFlag;
    private final Integer failFlag;
    private final Integer exceptionFlag;

    MinioOperationStatusEnum(String successMessage, String failMessage, String exceptionMessage) {
        this.successMessage = successMessage;
        this.failMessage = failMessage;
        this.exceptionMessage = exceptionMessage;
        this.successFlag = 1;
        this.failFlag = 0;
        this.exceptionFlag = -1;
    }
}
