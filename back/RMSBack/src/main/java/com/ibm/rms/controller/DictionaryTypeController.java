package com.ibm.rms.controller;

import com.ibm.rms.domain.ResponseResult;
import com.ibm.rms.pojo.DictionaryType;
import com.ibm.rms.pojo.dto.DicTypePageConditionDto;
import com.ibm.rms.service.DictionaryTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 数据字典类型管理功能控制层
 *
 * @author 刘嘉宁
 * @date 2022-12-02 22:22:49
 */
@RestController
@RequestMapping("/dicType")
public class DictionaryTypeController {

    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    /**
     * 根据分页和模糊查询数据获取数据字典类型表对应数据列表
     *
     * @param dto 页码、数据量、模糊查询值
     * @return 对应数据 List 列表
     */
    @PostMapping("/getDicTypeListByPageCondition")
    public ResponseResult<List<DictionaryType>> getDicTypeListByPageCondition(@RequestBody DicTypePageConditionDto dto) {
        List<DictionaryType> typeList = dictionaryTypeService.getDicTypeListByPageCondition(dto);
        return new ResponseResult<>(200, "获取数据字典类型列表成功", typeList);
    }

    /**
     * 获取全部数据字典类型表对应数据列表
     *
     * @return 数据 List 列表
     */
    @PostMapping("/getDicTypeList")
    public ResponseResult<List<DictionaryType>> getDicTypeList() {
        List<DictionaryType> typeList = dictionaryTypeService.getDicTypeList();
        return new ResponseResult<>(200, "获取数据字典类型列表成功", typeList);
    }

    /**
     * 获取当前数据字典类型总量
     *
     * @return 当前数据字典类型总量
     */
    @PostMapping("/getDicTypeTotalSizeByCondition")
    public ResponseResult<Long> getDicTypeTotalSizeByCondition(@RequestBody DicTypePageConditionDto dto) {
        Long totalSize = dictionaryTypeService.getDicTypeTotalSizeByCondition(dto);
        return new ResponseResult<>(200, "获取数据字典类型总量成功", totalSize);
    }

    /**
     * 根据 id 删除对应记录
     *
     * @param map 数据字典类型 id
     * @return 删除成功提示
     */
    @PostMapping(value = "/removeDicTypeById")
    public ResponseResult<Integer> removeDicTypeById(@RequestBody Map<String, String> map) {
        dictionaryTypeService.removeDicTypeById(map.get("id"));
        return new ResponseResult<>(200, "删除成功");
    }

    /**
     * 添加数据字典类型数据
     *
     * @param dictionaryType 添加内容
     * @return 添加成功提示
     */
    @PostMapping(value = "/addDicType")
    public ResponseResult<Integer> addDictionaryType(@RequestBody DictionaryType dictionaryType) {
        dictionaryTypeService.addDicType(dictionaryType);
        return new ResponseResult<>(200, "添加成功");
    }

    /**
     * 根据 id 返回对应数据字典类型数据
     *
     * @param map id
     * @return 对应数据
     */
    @PostMapping(value = "/getDicTypeById")
    public ResponseResult<DictionaryType> getDicTypeById(@RequestBody Map<String, String> map) {
        DictionaryType dictionaryType = dictionaryTypeService.getDicTypeById(map.get("id"));
        return new ResponseResult<>(200, "查询成功", dictionaryType);
    }

    /**
     * 编辑数据字典类型数据
     *
     * @param dictionaryType 修改后内容
     * @return 修改成功提示
     */
    @PostMapping(value = "/editDicType")
    public ResponseResult<DictionaryType> editDicType(@RequestBody DictionaryType dictionaryType) {
        dictionaryTypeService.editDicType(dictionaryType);
        return new ResponseResult<>(200, "修改成功");
    }

}
