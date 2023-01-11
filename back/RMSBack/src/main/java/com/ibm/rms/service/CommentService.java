package com.ibm.rms.service;

import com.ibm.rms.pojo.vo.CommentVo;

import java.util.List;

/**
 * 评论功能服务层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-18 18:11:57
 */
public interface CommentService {

    /**
     * 根据面试 id 获取对应所有评论信息
     *
     * @param id 面试 ID
     * @return 对应所有评论信息
     */
    List<CommentVo> getCommentList(String id);
}
