package com.ibm.rms.service;

import com.ibm.rms.pojo.DictionaryValue;
import com.ibm.rms.pojo.dto.DicValueAddDto;
import com.ibm.rms.pojo.dto.DicValuePageConditionDto;
import com.ibm.rms.pojo.vo.DicValueCheckableVo;
import com.ibm.rms.pojo.vo.DictionaryValueVo;

import java.util.List;

/**
 * 数据字典值管理数据服务层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-05 20:02:18
 */
public interface DictionaryValueService {

    /**
     * 根据分页和模糊查询数据获取数据字典值列表
     *
     * @param dto 分页和模糊查询数据
     * @return 数据列表
     */
    List<DictionaryValueVo> getDicValueList(DicValuePageConditionDto dto);

    /**
     * 获取数据字典值表数据总量
     *
     * @return 数据总量
     */
    Long getDicValueTotalSizeByCondition(DicValuePageConditionDto dto);

    /**
     * 添加数据字典值表数据
     *
     * @return 成功提示
     */
    void addDicValue(DicValueAddDto dto);

    /**
     * 根据 id 删除数据字典值数据
     *
     * @param id 数据字典值 ID
     */
    void removeDicValueById(String id);

    /**
     * 根据 id 获取数据字典值表数据
     *
     * @param id 数据字典值 ID
     * @return 对应数据
     */
    DictionaryValue getDicValueById(String id);

    /**
     * 根据 id 修改数据字典值数据
     *
     * @param dictionaryValue 新的数据字典值数据
     */
    void editDicValue(DictionaryValue dictionaryValue);

    /**
     * 批量删除数据字典值
     *
     * @param idList 数据字典值 ID 列表
     */
    void removeDicValueBatch(List<String> idList);

    /**
     * 根据数据字典类型 typeCode 获取对应数据字典值列表
     *
     * @param typeCode 数据字典类型 typeCode
     * @return 对应数据字典值列表
     */
    List<DictionaryValue> getDictionaryValueListByTypeCode(String typeCode);

    /**
     * 根据数据字典类型 typeCode 获取对应带有可选项的数据字典值列表
     *
     * @param typeCode 数据字典类型 typeCode
     * @return 对应带有可选项的数据字典值列表
     */
    List<DicValueCheckableVo> getDicValueCheckableVoByTypeCode(String typeCode);
}
