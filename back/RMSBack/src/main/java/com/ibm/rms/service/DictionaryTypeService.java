package com.ibm.rms.service;

import com.ibm.rms.pojo.DictionaryType;
import com.ibm.rms.pojo.dto.DicTypePageConditionDto;

import java.util.List;

/**
 * 数据字典类型管理数据服务层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-04 16:02:18
 */
public interface DictionaryTypeService {
    /**
     * 根据分页和模糊查询数据获取对应数据列表
     *
     * @param dto 页码、数据量、模糊查询值
     * @return 数据列表
     */
    List<DictionaryType> getDicTypeListByPageCondition(DicTypePageConditionDto dto);

    /**
     * 获取当前数据字典类型表数据总量
     *
     * @return 总量
     */
    Long getDicTypeTotalSizeByCondition(DicTypePageConditionDto dto);

    /**
     * 根据 id 删除对应数据, 在数据字典值表中无对应记录的前提下
     *
     * @param id 数据字典类型 id
     */
    void removeDicTypeById(String id);

    /**
     * 添加数据字典类型表数据
     *
     * @param dictionaryType 添加内容
     */
    void addDicType(DictionaryType dictionaryType);

    /**
     * 根据 id 查询对应数据字典类型数据
     *
     * @param id 数据字典类型 id
     * @return 对应数据
     */
    DictionaryType getDicTypeById(String id);

    /**
     * 根据 id 修改数据字典类型表数据
     *
     * @param dictionaryType 修改内容
     */
    void editDicType(DictionaryType dictionaryType);

    /**
     * 获取全部数据字典类型表对应数据列表
     *
     * @return 数据 List 列表
     */
    List<DictionaryType> getDicTypeList();

}
