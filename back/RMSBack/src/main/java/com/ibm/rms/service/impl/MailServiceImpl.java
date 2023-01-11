package com.ibm.rms.service.impl;

import com.ibm.rms.domain.LoginUser;
import com.ibm.rms.domain.Mail;
import com.ibm.rms.mapper.InterviewMapper;
import com.ibm.rms.mapper.UserMapper;
import com.ibm.rms.pojo.dto.InterviewEditDto;
import com.ibm.rms.pojo.dto.ResumeInterviewDto;
import com.ibm.rms.pojo.vo.InterviewResumeVo;
import com.ibm.rms.service.MailService;
import com.ibm.rms.mq.MailPublisher;
import com.ibm.rms.pojo.Interview;
import com.ibm.rms.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 邮件发送服务层接口实现类
 *
 * @author 刘嘉宁
 * @date 2022-12-21 12:11:42
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailPublisher mailPublisher;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private InterviewMapper interviewMapper;

    Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Override
    public void mailSend4editInterviewInfo(InterviewEditDto dto) {
        Mail mail = new Mail();
        // 获取当前操作用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mail.setFrom(loginUser.getUser().getEmail());
        mail.setSubject("来自 " + loginUser.getUser().getName() + " 的任务分配");
        // 发送邮件
        if (dto.getReviewer() != null && !"".equals(dto.getReviewer())) {
            User user = userMapper.selectByPrimaryKey(dto.getReviewer());
            mail.setTo(user.getEmail());
            mail.setText("详情请查看 面试一览 => 面试编号: " + dto.getId().substring(0, 18));
            mailPublisher.process(mail);
        }
        if (dto.getInterviewer1() != null && !"".equals(dto.getInterviewer1())) {
            User user = userMapper.selectByPrimaryKey(dto.getInterviewer1());
            mail.setTo(user.getEmail());
            mail.setText("详情请查看 面试一览 => 面试编号: " + dto.getId().substring(0, 18));
            mailPublisher.process(mail);
        }
        if (dto.getInterviewer2() != null && !"".equals(dto.getInterviewer2())) {
            User user = userMapper.selectByPrimaryKey(dto.getInterviewer2());
            mail.setTo(user.getEmail());
            mail.setText("详情请查看 面试一览 => 面试编号: " + dto.getId().substring(0, 18));
            mailPublisher.process(mail);
        }
        if (dto.getInterviewer3() != null && !"".equals(dto.getInterviewer3())) {
            User user = userMapper.selectByPrimaryKey(dto.getInterviewer3());
            mail.setTo(user.getEmail());
            mail.setText("详情请查看 面试一览 => 面试编号: " + dto.getId().substring(0, 18));
            mailPublisher.process(mail);
        }
        if (dto.getInterviewer4() != null && !"".equals(dto.getInterviewer4())) {
            User user = userMapper.selectByPrimaryKey(dto.getInterviewer4());
            mail.setTo(user.getEmail());
            mail.setText("详情请查看 面试一览 => 面试编号: " + dto.getId().substring(0, 18));
            logger.info("正在向 " + user.getName() + " 发送邮件");
            mailPublisher.process(mail);
        }
    }

    @Override
    public void mailSend4editReviewerAutoAssignByIdList(List<InterviewResumeVo> voList) {
        Mail mail = new Mail();
        // 获取当前操作用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mail.setFrom(loginUser.getUser().getEmail());
        mail.setSubject("来自 " + loginUser.getUser().getName() + " 的任务分配");
        // 发送邮件
        for (InterviewResumeVo vo : voList) {
            Interview interview = interviewMapper.selectByPrimaryKey(vo.getId());
            User user = userMapper.selectByPrimaryKey(interview.getReviewer());
            mail.setTo(user.getEmail());
            mail.setText("详情请查看 面试一览 => 面试编号: " + vo.getId().substring(0, 18));
            logger.info("正在向 " + user.getName() + " 发送邮件");
            mailPublisher.process(mail);
        }
    }

    @Override
    public void mailSend4addResumeInterview(ResumeInterviewDto dto, String id) {
        Mail mail = new Mail();
        // 获取当前操作用户信息
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mail.setFrom(loginUser.getUser().getEmail());
        mail.setSubject("来自 " + loginUser.getUser().getName() + " 的任务分配");
        // 发送邮件
        User user = userMapper.selectByPrimaryKey(dto.getReviewer());
        mail.setTo(user.getEmail());
        mail.setText("详情请查看 面试一览 => 面试编号: " + id.substring(0, 18));
        logger.info("正在向 " + user.getName() + " 发送邮件");
        mailPublisher.process(mail);
    }

}
