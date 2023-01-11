package com.ibm.rms.service.impl;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.exception.DictionaryException;
import com.ibm.rms.pojo.DictionaryType;
import com.ibm.rms.mapper.DictionaryTypeMapper;
import com.ibm.rms.mapper.DictionaryValueMapper;
import com.ibm.rms.pojo.dto.DicTypePageConditionDto;
import com.ibm.rms.service.DictionaryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典类型管理数据服务层接口实现类
 *
 * @author 刘嘉宁
 * @date 2022-12-04 16:02:18
 */
@Service
public class DictionaryTypeServiceImpl implements DictionaryTypeService {

    @Autowired
    private DictionaryTypeMapper dictionaryTypeMapper;

    @Autowired
    private DictionaryValueMapper dictionaryValueMapper;

    @Override
    public List<DictionaryType> getDicTypeListByPageCondition(DicTypePageConditionDto dto) {
        // 执行查询操作
        List<DictionaryType> typeList = dictionaryTypeMapper.selectAllByPageCondition(dto);
        // 如果查询出来的数据为 null (空数据应为 [] ) 抛出异常
        if (typeList == null) {
            throw new DictionaryException(ErrorResultEnum.DIC_TYPE_SELECT_FAIL);
        }
        return typeList;
    }

    @Override
    public Long getDicTypeTotalSizeByCondition(DicTypePageConditionDto dto) {
        // 执行查询操作
        return dictionaryTypeMapper.selectTotalSizeByCondition(dto);
    }

    @Override
    public void removeDicTypeById(String id) {
        // 查询数据字典值表中是否有对应数据
        int count = dictionaryValueMapper.countByTypeId(id);
        // 如果数据字典值表中存在对应数据则抛出异常
        if (count != 0) {
            throw new DictionaryException(ErrorResultEnum.DIC_TYPE_DELETE_FAIL_CAUSE_VALUE);
        }
        // 执行删除操作
        int result = dictionaryTypeMapper.deleteByPrimaryKey(id);
        // 如果删除失败则抛出异常
        if (result != 1) {
            throw new DictionaryException(ErrorResultEnum.DIC_TYPE_DELETE_FAIL);
        }
    }

    @Override
    public void addDicType(DictionaryType dictionaryType) {
        // 执行添加功能
        int result = dictionaryTypeMapper.insertSelective(dictionaryType);
        // 判断是否添加成功, 失败时抛出异常
        if (result != 1) {
            throw new DictionaryException(ErrorResultEnum.DIC_TYPE_INSERT_FAIL);
        }
    }

    @Override
    public DictionaryType getDicTypeById(String id) {
        // 执行查询
        DictionaryType dictionaryType = dictionaryTypeMapper.selectByPrimaryKey(id);
        // 如果没查到对应数据则抛出异常
        if (dictionaryType == null) {
            throw new DictionaryException(ErrorResultEnum.DIC_TYPE_SELECT_FAIL);
        }
        return dictionaryType;
    }

    @Override
    public void editDicType(DictionaryType dictionaryType) {
        // 执行修改
        int result = dictionaryTypeMapper.updateByPrimaryKeySelective(dictionaryType);
        // 如果修改失败则抛出异常
        if (result != 1) {
            throw new DictionaryException(ErrorResultEnum.DIC_TYPE_UPDATE_FAIL);
        }
    }

    @Override
    public List<DictionaryType> getDicTypeList() {
        // 执行查询操作
        List<DictionaryType> typeList = dictionaryTypeMapper.selectAll();
        // 如果查询出来的数据为 null (空数据应为 [] ) 抛出异常
        if (typeList == null) {
            throw new DictionaryException(ErrorResultEnum.DIC_TYPE_SELECT_FAIL);
        }
        return typeList;
    }

}
