package com.ibm.rms.mapper;

import com.ibm.rms.pojo.Comment;
import com.ibm.rms.pojo.vo.CommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论数据访问层
 *
 * @author 刘嘉宁
 * @date 2022-12-18 16:44:39
 */
public interface CommentMapper {

    int deleteByPrimaryKey(byte[] id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(byte[] id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);


    /**
     * 插入备注数据
     *
     * @param id 当前操作人 ID
     * @param comment 备注内容、类型、面试表 ID
     * @return 影响条数
     */
    int insertCommentSelective(@Param("id") String id, @Param("comment") Comment comment);

    /**
     * 根据面试 ID 查询对应评论数据
     *
     * @param id 面试 ID
     * @return 对应评论数据列表
     */
    List<CommentVo> selectAllByInterviewId(String id);
}