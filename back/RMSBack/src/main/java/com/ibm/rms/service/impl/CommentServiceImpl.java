package com.ibm.rms.service.impl;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.exception.CommentException;
import com.ibm.rms.mapper.CommentMapper;
import com.ibm.rms.pojo.vo.CommentVo;
import com.ibm.rms.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论功能服务层接口实现类
 *
 * @author 刘嘉宁
 * @date 2022-12-18 18:11:57
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<CommentVo> getCommentList(String id) {
        // 执行查询操作
        List<CommentVo> commentList = commentMapper.selectAllByInterviewId(id);
        // 如果查询失败则抛出异常
        if (commentList == null) {
            throw new CommentException(ErrorResultEnum.COMMENT_SELECT_FAIL);
        }
        return commentList;
    }

}
