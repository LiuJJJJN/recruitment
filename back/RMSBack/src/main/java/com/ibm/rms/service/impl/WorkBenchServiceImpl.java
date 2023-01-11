package com.ibm.rms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.domain.LoginUser;
import com.ibm.rms.exception.WorkBenchException;
import com.ibm.rms.mapper.InterviewMapper;
import com.ibm.rms.mapper.UserMapper;
import com.ibm.rms.pojo.dto.InterviewConditionDto;
import com.ibm.rms.pojo.vo.InterviewVo;
import com.ibm.rms.service.WorkBenchService;
import com.ibm.rms.utils.RedisCacheUtil;

/**
 * 工作台服务层实现
 *
 * @author 耿晨
 * @date 2022-12-3 11:46:30
 */

@Service
@PropertySource("classpath:constValue.properties")
public class WorkBenchServiceImpl implements WorkBenchService {

	 // 注入 AuthenticationManager 进行用户认证
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCacheUtil redisCache;
	
	@Autowired
	private InterviewMapper interviewMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	// 等待分配 Reviewer的数据字典值
    @Value("${DISTRIBUTION_REVIEWER}")
    private Integer DISTRIBUTION_REVIEWER;
    
    // 等待简历 Review的数据字典值
    @Value("${INTERVIEW_REVIEW}")
    private Integer INTERVIEW_REVIEW;
    
    // 等待指定面试官的数据字典值
    @Value("${ASSIGN_GL}")
    private Integer ASSIGN_GL;
    
    // 等待确定面试时间的数据字典值
    @Value("${COMFIRM_INTERVIEW_TIME}")
    private Integer COMFIRM_INTERVIEW_TIME;
    
    // 等待面试的数据字典值
    @Value("${INTERVIEW}")
    private Integer INTERVIEW;
    
    // 等待面试提交反馈的数据字典值
    @Value("${INTERVIEW_FEEDBACK}")
    private Integer INTERVIEW_FEEDBACK;
    
    // 面试 OK的数据字典值
    @Value("${OFFER}")
    private Integer OFFER;
    
    // 角色为 reviewer 的角色表 name
    @Value("${REVIEWER_ROLE_NAME}")
    private String REVIEWER_ROLE_NAME;

    // 角色为 gl 的角色表 name
    @Value("${GL_ROLE_NAME}")
    private String GL_ROLE_NAME;

    // 角色为 pmo 的角色表 name
    @Value("${PMO_ROLE_NAME}")
    private String PMO_ROLE_NAME;
    
	/**
	 * 获取任务量
	 * @param 分页数据和日期
	 * @return  任务量列表，第一项是总任务量，第二项是用户的任务量，第三项是今天的任务量
	 */
	@Override
	public Map<String,Object> getInterviewStates(InterviewConditionDto dto) {
		// 存储任务量用的map
		Map<String,Object> missions=new HashMap<>();
		// 从表里获取总任务量
		List<Map<String, Long>> missionList = interviewMapper.countInterviewStates();
		// 如果查询出来的数据为 null (空数据应为 [] ) 抛出异常
		if(missionList==null) {
			throw new WorkBenchException(ErrorResultEnum.WORK_BENCH_MISSIONS_EXCEPTION);
		}
		// 对总任务量进行格式转换
		Map<Object,Object> allMissions=new HashMap<Object,Object>();
		for (Map<String, Long> map : missionList) {
			allMissions.put(map.get("K"),map.get("V"));
		}
		// 将总任务量加入到map里
		missions.put("allMissions",allMissions);
		 // 获取到当前用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId();
        // 获取当前用户的角色列表
        List<String> userRoles=userMapper.selectRoleById(userId);
        // 根据用户角色获取其应该完成的任务类型
        List<Integer> userRoleMission=new ArrayList<>();
        missions.put("userRoleMission",userRoleMission);
        for (String role : userRoles) {
			if(role.equals(PMO_ROLE_NAME)) {
				userRoleMission.add(DISTRIBUTION_REVIEWER);
				userRoleMission.add(ASSIGN_GL);
				userRoleMission.add(OFFER);
			}else if(role.equals(REVIEWER_ROLE_NAME)){
				userRoleMission.add(INTERVIEW_REVIEW);
			}else if(role.equals(GL_ROLE_NAME)){
				userRoleMission.add(COMFIRM_INTERVIEW_TIME);
				userRoleMission.add(INTERVIEW);
				userRoleMission.add(INTERVIEW_FEEDBACK);
			}
		}
        // 判断任务类型，若没有需要完成的任务直接设成空map
        if(userRoleMission.size()<=0) {
        	missions.put("userMissions",new HashMap<>());
        	// 0表示今日任务都完成了
        	missions.put("todayMission",0);
        }else {
        	// 根据用户和用户应该完成的任务查询任务量
        	missionList = interviewMapper.countInterviewStatesByUser(userId,userRoleMission);
        	// 如果查询出来的数据为 null (空数据应为 [] ) 抛出异常
        	if(missionList==null) {
    			throw new WorkBenchException(ErrorResultEnum.WORK_BENCH_MISSIONS_EXCEPTION);
    		}
        	// 对任务量进行格式转换
        	Map<Object,Object> userMissions=new HashMap<Object,Object>();
        	for (Map<String, Long> map : missionList) {
    			userMissions.put(map.get("K"),map.get("V"));
    		}
        	// 将当前用户任务量加入进总任务里
        	missions.put("userMissions",userMissions);
        	// 获取查询日期
        	String date=dto.getDate();
        	// 根据日期和用户和用户应该完成的任务查询指定日期的所有任务
        	List<InterviewVo> todayMission =interviewMapper.selectInterviewByUserToday(userId, userRoleMission,date,dto);
        	// 如果查询出来的数据为 null (空数据应为 [] ) 抛出异常
        	if(todayMission==null) {
    			throw new WorkBenchException(ErrorResultEnum.WORK_BENCH_MISSIONS_EXCEPTION);
    		}
        	// 将指定日期的用户所有任务加入进总任务里，若没有任务则设成0
        	if(todayMission.isEmpty()) {
        		missions.put("todayMission",0);
        	}else {
        		missions.put("todayMission",todayMission);
        	}
        }
        
		return missions;
	}
	

	
}
