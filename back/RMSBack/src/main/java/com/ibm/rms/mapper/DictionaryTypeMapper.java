package com.ibm.rms.mapper;

import com.ibm.rms.pojo.DictionaryType;
import com.ibm.rms.pojo.dto.DicTypePageConditionDto;

import java.util.List;

/**
 * 数据字典类型管理数据访问层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-04 00:02:18
 */
public interface DictionaryTypeMapper {

    /**
     * 根据 id 删除对应数据, 在数据字典值表中无对应记录的前提下
     *
     * @param id id
     * @return 影响条数
     */
    int deleteByPrimaryKey(String id);

    int insert(DictionaryType record);

    /**
     * 插入一条数据字典类型数据
     *
     * @param record 数据内容
     * @return 影响条数
     */
    int insertSelective(DictionaryType record);

    /**
     * 根据 id 查询对应数据字典类型数据
     *
     * @param id id
     * @return 对应数据
     */
    DictionaryType selectByPrimaryKey(String id);

    /**
     * 根据 id 修改对应数据
     *
     * @param record id 及要修改的数据
     * @return 影响条数
     */
    int updateByPrimaryKeySelective(DictionaryType record);

    int updateByPrimaryKey(DictionaryType record);

    /**
     * 根据分页数据和模糊查询数据查询对应数据列表
     *
     * @param dto 页码、数据量、模糊查询值
     * @return 数据列表
     */
    List<DictionaryType> selectAllByPageCondition(DicTypePageConditionDto dto);

    /**
     * 获取当前数据字典类型表的数据总量
     *
     * @return 数据总量
     */
    Long selectTotalSizeByCondition(DicTypePageConditionDto dto);

    /**
     * 查询所有数据字典类型数据列表
     *
     * @return 数据字典类型列表
     */
    List<DictionaryType> selectAll();

}
