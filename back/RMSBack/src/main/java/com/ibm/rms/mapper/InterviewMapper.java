package com.ibm.rms.mapper;

import com.ibm.rms.pojo.Interview;
import com.ibm.rms.pojo.dto.InterviewConditionDto;
import com.ibm.rms.pojo.dto.InterviewEditDto;
import com.ibm.rms.pojo.dto.InterviewOverviewPageConditionDto;
import com.ibm.rms.pojo.vo.InterviewResumeExcelVo;
import com.ibm.rms.pojo.vo.InterviewResumeVo;
import com.ibm.rms.pojo.vo.InterviewVo;
import com.ibm.rms.pojo.vo.ResumeInterviewVo;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 面试数据访问层
 *
 * @author 刘嘉宁 耿晨 陈亚明
 * @date 2022-12-3 11:46:30
 */
public interface InterviewMapper {
    int deleteByPrimaryKey(String id);

    int insert(Interview record);

    int insertSelective(Interview record);

    Interview selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Interview record);

    int updateByPrimaryKey(Interview record);

    /**
     * 统计任务状态的数量
     *
     * @return 任务状态数量列表
     */
    List<Map<String, Long>> countInterviewStates();

    /**
     * 统计当前用户任务状态的数量
     *
     * @return 任务状态数量列表
     */
    List<Map<String, Long>> countInterviewStatesByUser(@Param("id") String id, @Param("list") List<Integer> list);


    /**
     * 统计当前用户今日任务
     *
     * @return 任务状态数量列表
     */
    List<InterviewVo> selectInterviewByUserToday(@Param("id") String id,
                                                 @Param("list") List<Integer> list,
                                                 @Param("date") String date,
                                                 @Param("dto") InterviewConditionDto dto);

    /**
     * 根据分页和模糊查询数据查询对应面试及简历信息数据列表
     *
     * @param dto 分页和模糊查询数据
     * @return 对应面试及简历信息数据列表
     */
    List<InterviewResumeVo> selectAllByPageCondition(@Param("dto") InterviewOverviewPageConditionDto dto,
                                                     @Param("typeCode") String typeCode);

    /**
     * 根据分页和模糊查询数据查询对应面试及简历信息数据总量
     *
     * @param dto 分页和模糊查询数据
     * @return 对应面试及简历信息数据总量
     */
    Long selectInterviewOverviewTotalSize(InterviewOverviewPageConditionDto dto);

    /**
     * 根据 id 列表查询对应的面试及简历数据列表
     *
     * @param idList 面试 ID 列表
     * @return 对应的面试及简历数据列表
     */
    List<InterviewResumeExcelVo> selectAllByIdList(List<String> idList);

    /**
     * 根据面试信息更新状态
     *
     * @param vo              面试信息, 其中包括面试 ID 和旧状态值
     * @param interviewStatus 新状态值
     * @param id              当前操作用户 id
     * @return 影响条数
     */
    int updateStatusByPrimary(@Param("vo") InterviewResumeVo vo,
                              @Param("status") Integer interviewStatus,
                              @Param("id") String id);

    /**
     * 查询 id 列表和对应面试状态数量
     *
     * @param idList ID 列表
     * @param status 状态代码
     * @return 对应数量
     */
    Long countByIdListAndStatus(@Param("idList") List<String> idList, @Param("status") String status);

    /**
     * 为指定 id 的面试自动分配当前任务量最少且担当过 reviewer 的 reviewer
     *
     * @param id         面试 ID
     * @param modifyDate 修改时间: 乐观锁标记
     * @param roleName   角色名
     * @param newStatus  新面试状态
     * @param userId     当前操作用户 id
     * @param userStatus 用户状态
     * @return 影响条数
     */
    int updateReviewerAutoAssign(@Param("id") String id,
                                 @Param("modifyDate") Date modifyDate,
                                 @Param("roleName") String roleName,
                                 @Param("newStatus") String newStatus,
                                 @Param("userId") String userId,
                                 @Param("userStatus") Integer userStatus);

    /**
     * 根据 dto 内容更新面试表
     *
     * @param dto    面试信息
     * @param id     当前操作人 id
     * @param status 修改后应处于的状态
     * @return 影响条数
     */
    int updateByDtoSelective(@Param("dto") InterviewEditDto dto, @Param("id") String id, @Param("status") String status);

    /**
     * 更新面试状态: Review OK or NG
     *
     * @param id        当前操作用户 ID
     * @param interview 面试 ID 其中包括新的 status 值
     * @return 影响条数
     */
    int updateStatusById(@Param("id") String id, @Param("interview") Interview interview);

    ResumeInterviewVo selectInterviewResumeByInterviewId(String id);

    /**
     * 获取 UUID
     *
     * @return 生成的 UUID 字符
     */
    String selectUUID();

    /**
     * 插入面试表数据
     *
     * @param interview 固定面试 ID 的面试信息
     * @return 影响条数
     */
    int insertSelective3CVUpload(Interview interview);
}
