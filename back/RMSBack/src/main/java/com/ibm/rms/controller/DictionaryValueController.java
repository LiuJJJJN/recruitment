package com.ibm.rms.controller;

import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.pojo.DictionaryValue;
import com.ibm.rms.pojo.dto.DicValueAddDto;
import com.ibm.rms.pojo.dto.DicValuePageConditionDto;
import com.ibm.rms.pojo.vo.DicValueCheckableVo;
import com.ibm.rms.pojo.vo.DictionaryValueVo;
import com.ibm.rms.service.DictionaryValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 数据字典值管理功能控制层
 *
 * @author 刘嘉宁，车馨心
 * @date 2022-12-04 23:23:21
 */
@RestController
@RequestMapping("/dicValue")
@PropertySource("classpath:constValue.properties")
public class DictionaryValueController {

    @Autowired
    private DictionaryValueService dictionaryValueService;

    // 用户状态的数据字典类型 code
    @Value("${USER_STATUS}")
    private String USER_STATUS;

    // 模糊搜索类型的数据字典类型 code
    @Value("${VAGUE_SEARCH}")
    private String VAGUE_SEARCH;

    // 性别的数据字典类型 code
    @Value("${SEX}")
    private String SEX;

    // 学历的数据字典类型 code
    @Value("${EDUCATION}")
    private String EDUCATION;

    // 技术能力的数据字典类型 code
    @Value("${TECH_ABILITY}")
    private String TECH_ABILITY;

    // 语言能力的数据字典类型 code
    @Value("${LANG_ABILITY}")
    private String LANG_ABILITY;

    // 是否服从的数据字典类型 code
    @Value("${SOURCE}")
    private String SOURCE;

    // 简历来源的数据字典类型 code
    @Value("${OBEY}")
    private String OBEY;

    // 反馈结果的数据字典类型 code
    @Value("${RESULT}")
    private String RESULT;
    // 考察能力的数据字典类型 code
    @Value("${ABILITY_ITEM}")
    private String ABILITY_ITEM;
    // 考察能力的数据字典类型 code
    @Value("${CONFIRM_ITEM}")
    private String CONFIRM_ITEM;

    // 面试状态的数据字典类型 code
    @Value("${IV_STATUS}")
    private String IV_STATUS;

    // 录入时间的数据字典类型 code
    @Value("${ENTRY_TIME}")
    private String ENTRY_TIME;

    // 毕业时间的数据字典类型 code
    @Value("${GRAD_TIME}")
    private String GRAD_TIME;

    /**
     * 根据分页和模糊查询数据获取数据字典值列表
     *
     * @param dto 分页和模糊查询数据
     * @return 数据列表
     */
    @PostMapping("/getDicValueList")
    public ResponseResult<List<DictionaryValueVo>> getDicValueList(@RequestBody DicValuePageConditionDto dto) {
        List<DictionaryValueVo> voList = dictionaryValueService.getDicValueList(dto);
        return new ResponseResult<>(200, "获取数据字典值列表成功", voList);
    }

    /**
     * 获取数据字典值表数据总量
     *
     * @return 数据总量
     */
    @PostMapping("/getDicValueTotalSizeByCondition")
    public ResponseResult<Long> getDicValueTotalSizeByCondition(@RequestBody DicValuePageConditionDto dto) {
        Long totalSize = dictionaryValueService.getDicValueTotalSizeByCondition(dto);
        return new ResponseResult<>(200, "获取数据字典值数据总量成功", totalSize);
    }

    /**
     * 添加数据字典值表数据
     *
     * @return 成功提示
     */
    @PostMapping("/addDicValue")
    public ResponseResult<Long> addDicValue(@RequestBody DicValueAddDto dto) {
        dictionaryValueService.addDicValue(dto);
        return new ResponseResult<>(200, "添加数据字典值数据成功");
    }

    /**
     * 添加数据字典值表数据
     *
     * @return 成功提示
     */
    @PostMapping("/removeDicValueById")
    public ResponseResult<Long> removeDicValueById(@RequestBody Map<String, String> map) {
        dictionaryValueService.removeDicValueById(map.get("id"));
        return new ResponseResult<>(200, "删除成功");
    }

    /**
     * 根据 id 获取数据字典值表数据
     *
     * @return 成功信息
     */
    @PostMapping("/getDicValueById")
    public ResponseResult<DictionaryValue> getDicValueById(@RequestBody Map<String, String> map) {
        DictionaryValue dictionaryValue = dictionaryValueService.getDicValueById(map.get("id"));
        return new ResponseResult<>(200, "获取成功", dictionaryValue);
    }

    /**
     * 根据 id 修改数据字典值数据
     *
     * @return 成功信息
     */
    @PostMapping("/editDicValue")
    public ResponseResult<DictionaryValue> editDicValue(@RequestBody DictionaryValue dictionaryValue) {
        dictionaryValueService.editDicValue(dictionaryValue);
        return new ResponseResult<>(200, "修改成功");
    }

    /**
     * 批量删除数据字典值数据
     *
     * @param idList 数据字典值 ID 列表
     * @return 删除成功提示
     */
    @PostMapping("/removeDicValueBatch")
    public ResponseResult<DictionaryValue> removeDicValueBatch(@RequestBody List<String> idList) {
        dictionaryValueService.removeDicValueBatch(idList);
        return new ResponseResult<>(200, "批量删除成功");
    }

    /**
     * 获取面试状态列表
     *
     * @return 面试状态列表
     */
    @PostMapping("/getIVStatusList")
    public ResponseResult<List<DicValueCheckableVo>> getIVStatusList() {
        List<DicValueCheckableVo> dictionaryValueList = dictionaryValueService.getDicValueCheckableVoByTypeCode(IV_STATUS);
        return new ResponseResult<>(200, "面试状态列表获取成功", dictionaryValueList);
    }

    /**
     * 获取录入时间列表
     *
     * @return 录入时间列表
     */
    @PostMapping("/getEntryTimeList")
    public ResponseResult<List<DicValueCheckableVo>> getEntryTimeList() {
        List<DicValueCheckableVo> dictionaryValueList = dictionaryValueService.getDicValueCheckableVoByTypeCode(ENTRY_TIME);
        return new ResponseResult<>(200, "录入时间列表获取成功", dictionaryValueList);
    }

    /**
     * 获取毕业时间列表
     *
     * @return 毕业时间列表
     */
    @PostMapping("/getGradTimeList")
    public ResponseResult<List<DicValueCheckableVo>> getGradTimeList() {
        List<DicValueCheckableVo> dictionaryValueList = dictionaryValueService.getDicValueCheckableVoByTypeCode(GRAD_TIME);
        return new ResponseResult<>(200, "毕业时间列表获取成功", dictionaryValueList);
    }

    /**
     * 获取用户状态的数据字典值
     *
     * @return 用户状态的数据字典值
     */
    @PostMapping("/getUserStatusList")
    public ResponseResult<List<DictionaryValue>> getUserStatusList() {
        List<DictionaryValue> dictionaryValueList = dictionaryValueService.getDictionaryValueListByTypeCode(USER_STATUS);
        return new ResponseResult<>(200, "获取用户状态列表成功", dictionaryValueList);
    }

    /**
     * 获取模糊搜索项列表
     *
     * @return 模糊搜索类型列表获
     */
    @PostMapping("/getVagueSelectItemList")
    public ResponseResult<List<DictionaryValue>> getVagueSelectItemList() {
        List<DictionaryValue> dictionaryValueList = dictionaryValueService.getDictionaryValueListByTypeCode(VAGUE_SEARCH);
        return new ResponseResult<>(200, "模糊搜索项列表获取成功", dictionaryValueList);
    }

    /**
     * 获取性别列表
     *
     * @return 性别列表
     */
    @PostMapping("/getSexList")
    public ResponseResult<List<DictionaryValue>> getSexList() {
        List<DictionaryValue> dictionaryValueList = dictionaryValueService.getDictionaryValueListByTypeCode(SEX);
        return new ResponseResult<>(200, "性别列表获取成功", dictionaryValueList);
    }

    /**
     * 获取学历列表
     *
     * @return 学历列表
     */
    @PostMapping("/getEducationList")
    public ResponseResult<List<DictionaryValue>> getEducationList() {
        List<DictionaryValue> dictionaryValueList = dictionaryValueService.getDictionaryValueListByTypeCode(EDUCATION);
        return new ResponseResult<>(200, "学历列表获取成功", dictionaryValueList);
    }

    /**
     * 获取技术能力列表
     *
     * @return 技术能力列表
     */
    @PostMapping("/getTechAbilityList")
    public ResponseResult<List<DictionaryValue>> getTechAbilityList() {
        List<DictionaryValue> dictionaryValueList = dictionaryValueService.getDictionaryValueListByTypeCode(TECH_ABILITY);
        return new ResponseResult<>(200, "技术能力列表获取成功", dictionaryValueList);
    }

    /**
     * 获取语言能力列表
     *
     * @return 语言能力列表
     */
    @PostMapping("/getLangAbilityList")
    public ResponseResult<List<DictionaryValue>> getLangAbilityList() {
        List<DictionaryValue> dictionaryValueList = dictionaryValueService.getDictionaryValueListByTypeCode(LANG_ABILITY);
        return new ResponseResult<>(200, "语言能力列表获取成功", dictionaryValueList);
    }

    /**
     * 获取简历来源列表
     *
     * @return 简历来源列表
     */
    @PostMapping("/getSourceList")
    public ResponseResult<List<DictionaryValue>> getSourceList() {
        List<DictionaryValue> dictionaryValueList = dictionaryValueService.getDictionaryValueListByTypeCode(SOURCE);
        return new ResponseResult<>(200, "简历来源列表获取成功", dictionaryValueList);
    }

    /**
     * 获取是否服从列表
     *
     * @return 是否服从列表
     */
    @PostMapping("/getObeyList")
    public ResponseResult<List<DictionaryValue>> getObeyList() {
        List<DictionaryValue> dictionaryValueList = dictionaryValueService.getDictionaryValueListByTypeCode(OBEY);
        return new ResponseResult<>(200, "是否服从列表获取成功", dictionaryValueList);
    }

    /**
     * 获取反馈结果列表
     *
     * @return 反馈结果列表
     */
    @PostMapping("/getResultList")
    public ResponseResult<List<DictionaryValue>> getResultList() {
        List<DictionaryValue> dictionaryValueList = dictionaryValueService.getDictionaryValueListByTypeCode(RESULT);
        return new ResponseResult<>(200, "反馈结果列表获取成功", dictionaryValueList);
    }
    /**
     * 获取考察能力列表
     *
     * @return 考察能力列表
     */
    @PostMapping("/getAbility_item")
    public ResponseResult<List<DictionaryValue>> getAbility_item() {
        List<DictionaryValue> dictionaryValueList = dictionaryValueService.getDictionaryValueListByTypeCode(ABILITY_ITEM);
        return new ResponseResult<>(200, "考察能力列表获取成功", dictionaryValueList);
    }
    /**
     * 获取确认事项列表
     *
     * @return 确认事项列表
     */
    @PostMapping("/getConfirm_item")
    public ResponseResult<List<DictionaryValue>> getConfirm_item() {
        List<DictionaryValue> dictionaryValueList = dictionaryValueService.getDictionaryValueListByTypeCode(CONFIRM_ITEM);
        return new ResponseResult<>(200, "确认事项列表获取成功", dictionaryValueList);
    }
}
