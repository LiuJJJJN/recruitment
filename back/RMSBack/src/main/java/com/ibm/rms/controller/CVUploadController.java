package com.ibm.rms.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.ibm.rms.pojo.dto.ResumeInterviewDto;
import com.ibm.rms.service.MailService;
import com.ibm.rms.utils.FileUtils;
import com.ibm.rms.utils.MinioUtils;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.rms.service.CVUploadService;
import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.exception.CVUploadException;
import com.ibm.rms.pojo.DictionaryValue;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cvUpload")
public class CVUploadController {
	@Autowired
	private CVUploadService cvUploadService;

	@Autowired
	private MinioUtils minioUtils;

	@Autowired
	private MailService mailService;

	/**
	 * 获取学历列表, 无用接口待删除
	 *
	 * @return null
	 */
	@PostMapping("/getEducationList")
	public ResponseResult<Object> getEducationList() {
		List<DictionaryValue> dictionaryValueList = cvUploadService.getEducationList();

		return new ResponseResult<>(200, "学历列表获取成功", dictionaryValueList);
	}

	/**
	 * 上传简历文件
	 *
	 * @param file 简历文件
	 * @return 对象存储中的文件名
	 * @throws Exception 获取文件 url 失败时抛出异常
	 */
	@PostMapping("/resumeUpload")
	public String cvUpload(MultipartFile file) throws Exception {
		// 重命名文件 年月日时分秒+文件名
		String newName = FileUtils.formatName(file.getOriginalFilename());
		// 上传 minio
		InputStream inputStream = file.getInputStream();
		minioUtils.putObject("rms", newName, inputStream, file.getSize(), "application/octet-stream");
		// 判断是否上传成功
		String url = null;
		try {
			url = minioUtils.getObjectUrl("rms", newName);
		} catch (Exception e) {
			throw new CVUploadException(ErrorResultEnum.CV_UPLOAD_FAIL);
		}
		return newName;
	}

	/**
	 * 简历数据保存
	 *
	 * @param dto 简历信息
	 * @return 成功提示
	 */
	@PostMapping("/addResumeInterview")
	public ResponseResult<Object> addResumeInterview(@RequestBody ResumeInterviewDto dto) {
		// 向数据库添加数据, 并返回面试 ID
		String id = cvUploadService.addResumeInterview(dto);
		// 判断是否分配了 Reviewer
		if (dto.getReviewer() != null && !"".equals(dto.getReviewer())) {
			// 向消息队列添加邮件发送请求
			mailService.mailSend4addResumeInterview(dto, id);
		}
		return new ResponseResult<>(200, "保存成功");
	}

}
