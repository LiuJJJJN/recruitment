package com.ibm.rms.controller;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.exception.CommentException;
import com.ibm.rms.pojo.vo.CommentVo;
import com.ibm.rms.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 评论功能控制层
 *
 * @author 刘嘉宁
 * @date 2022-12-18 18:09:34
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 根据面试 id 获取对应评论列表
     *
     * @param map 面试 ID
     * @return 对应评论列表
     */
    @PostMapping("/getCommentList")
    public ResponseResult<List<CommentVo>> getCommentList(@RequestBody Map<String, String> map) {
        // 获取面试 id
        String id = null;
        try {
            id = map.get("id");
        } catch (Exception e) {
            throw new CommentException(ErrorResultEnum.PARAM_PARSE_EXCEPTION);
        }
        List<CommentVo> commentList = commentService.getCommentList(id);
        return new ResponseResult<>(200, "获取评论列表成功", commentList);
    }

}
