package com.ibm.rms.mapper;

import com.ibm.rms.pojo.Resume;
import com.ibm.rms.pojo.dto.ResumeInterviewDto;
import org.apache.ibatis.annotations.Param;

/**
 * 简历数据访问层
 *
 * @author 刘嘉宁
 * @date 2022-12-18 12:39:28
 */
public interface ResumeMapper {

    int deleteByPrimaryKey(String id);

    int insert(Resume record);

    int insertSelective(Resume record);

    Resume selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Resume record);

    int updateByPrimaryKey(Resume record);

    /**
     * 根据 面试 ID 查询简历信息
     *
     * @param interviewId 面试 ID
     * @return 简历信息
     */
    Resume selectByInterviewId(String interviewId);

    /**
     * 更新简历信息
     *
     * @param id 当前操作人 ID
     * @param data 简历信息
     * @return 影响条数
     */
    int updateByPrimaryKeySelectiveReview(@Param("id") String id, @Param("data") Resume data);

    /**
     * 根据简历上传表单项添加简历数据
     *
     * @param dto      简历上传表单项
     * @param resumeId 简历 ID
     * @param userId   创建人 ID
     * @return 影响条数
     */
    int insertSelectiveByResumeInterviewDto(@Param("dto") ResumeInterviewDto dto,
                                            @Param("resumeId") String resumeId,
                                            @Param("userId") String userId);
}
