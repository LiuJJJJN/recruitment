package com.ibm.rms.mapper;

import com.ibm.rms.pojo.DictionaryValue;
import com.ibm.rms.pojo.dto.DicValueAddDto;
import com.ibm.rms.pojo.dto.DicValuePageConditionDto;
import com.ibm.rms.pojo.vo.DicValueCheckableVo;
import com.ibm.rms.pojo.vo.DictionaryValueVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据字典值管理数据访问层接口
 *
 * @author 刘嘉宁
 * @date 2022-12-05 00:02:18
 */
public interface DictionaryValueMapper {

    int deleteByPrimaryKey(String id);

    int insert(DictionaryValue record);

    /**
     * 添加数据字典值表数据
     *
     * @param record 添加内容
     * @return 影响条数
     */
    int insertSelective(DicValueAddDto record);

    /**
     * 根据数据字典值 id 查询对应数据
     *
     * @param id 数据字典值 ID
     * @return 对应数据
     */
    DictionaryValue selectByPrimaryKey(String id);

    /**
     * 根据 id 更新数据字典值数据
     *
     * @param record 新的数据字典值数据
     * @return 影响条数
     */
    int updateByPrimaryKeySelective(DictionaryValue record);

    int updateByPrimaryKey(DictionaryValue record);

    /**
     * 根据数据字典类型 id 查询对应数据字典值表对应记录数量
     *
     * @param id 数据字典类型 id
     * @return 对应记录数量
     */
    int countByTypeId(String id);

    /**
     * 根据分页和模糊查询数据查询对应数据列表
     *
     * @param dto 分页和模糊查询数据
     * @return 对应数据列表
     */
    List<DictionaryValueVo> selectAllByPageCondition(DicValuePageConditionDto dto);

    /**
     * 查询数据字典值表数据总量
     *
     * @return 数据总量
     */
    Long selectTotalSizeByCondition(DicValuePageConditionDto dto);

    /**
     * 查询相同类型 id 和 key 的数量
     *
     * @param typeId   类型 ID
     * @param valueKey 数据字典值 key
     * @return 对应数量
     */
    long countByTypeIdAndValueKey(@Param("typeId") String typeId, @Param("valueKey") String valueKey);

    /**
     * 根据数据字典类型 code 查询数据字典值列表
     *
     * @return 对应查询数据字典值列表
     */
    List<DictionaryValue> selectByDicTypeCode(String typeCode);

    /**
     * 根据数据字典类型 code 查询数据字典值列表
     *
     * @return 对应查询数据字典值列表
     */
    List<DicValueCheckableVo> selectByDicTypeCode2Checkable(String typeCode);
}