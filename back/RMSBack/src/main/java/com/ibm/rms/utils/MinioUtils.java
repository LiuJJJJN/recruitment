package com.ibm.rms.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ibm.rms.domain.MinioProp;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * minio操作类
 */
@Component
public class MinioUtils {
	@Autowired
	private MinioClient client;
	@Autowired
	private MinioProp minioProp;

	/**
	 * 创建桶
	 *
	 * @param bucketName 桶名称
	 */
	@SneakyThrows
	public void createBucket(String bucketName) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {
		boolean found = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
		if (!found) {
			client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).region("cn-beijing").build());
		}
	}

	/**
	 * 删除桶
	 *
	 * @param bucketName 桶名称
	 */
	@SneakyThrows
	public void removeBucket(String bucketName) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {
		client.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
	}

	/**
	 * 获取文件信息
	 *
	 * @param bucketName 桶名称
	 * @param objectName 文件名称
	 * @return
	 */
	@SneakyThrows
	public Object getObjectInfo(String bucketName, String objectName) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {
		return client.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
	}

	/**
	 * 上传文件
	 *
	 * @param bucketName 桶名称
	 * @param objectName 文件名
	 * @param stream     流
	 * @param fileSize   文件大小
	 * @param type       文件类型
	 */
	public void putObject(String bucketName, String objectName, InputStream stream, Long fileSize, String type)
			throws Exception {
		client.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, fileSize, -1)
				.contentType(type).build());
	}

	/**
	 * 判断文件夹是否存在
	 *
	 * @param bucketName 桶名称
	 * @param prefix     文件夹名字
	 * @return
	 */
	@SneakyThrows
	public Boolean folderExists(String bucketName, String prefix) throws JsonParseException, InvalidKeyException, JsonMappingException, ErrorResponseException, IllegalArgumentException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException {
		Iterable<Result<Item>> results = client
				.listObjects(ListObjectsArgs.builder().bucket(bucketName).prefix(prefix).recursive(false).build());
		for (Result<Item> result : results) {
			Item item = result.get();
			if (item.isDir()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param bucketName 桶名称
	 * @param path       路径
	 */
	@SneakyThrows
	public void createFolder(String bucketName, String path) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {
		client.putObject(PutObjectArgs.builder().bucket(bucketName).object(path)
				.stream(new ByteArrayInputStream(new byte[] {}), 0, -1).build());
	}

	/**
	 * 获取文件在minio在服务器上的外链
	 * 
	 * @param bucketName 桶名称
	 * @param objectName 文件名
	 * @return
	 */
	@SneakyThrows
	public String getObjectUrl(String bucketName, String objectName) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, ServerException, IllegalArgumentException, IOException {
		return client.getPresignedObjectUrl(
				GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName).object(objectName).build());
	}

}
