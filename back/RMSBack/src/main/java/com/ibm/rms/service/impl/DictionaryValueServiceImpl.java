package com.ibm.rms.service.impl;

import com.ibm.rms.domain.ErrorResultEnum;
import com.ibm.rms.exception.DictionaryException;
import com.ibm.rms.pojo.DictionaryValue;
import com.ibm.rms.mapper.DictionaryValueMapper;
import com.ibm.rms.pojo.dto.DicValueAddDto;
import com.ibm.rms.pojo.dto.DicValuePageConditionDto;
import com.ibm.rms.pojo.vo.DicValueCheckableVo;
import com.ibm.rms.pojo.vo.DictionaryValueVo;
import com.ibm.rms.service.DictionaryValueService;
import com.ibm.rms.utils.RedisCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 数据字典值管理数据服务层接口实现类
 *
 * @author 刘嘉宁
 * @date 2022-12-05 20:02:18
 */
@Service
@PropertySource("classpath:constValue.properties")
public class DictionaryValueServiceImpl implements DictionaryValueService {

    @Autowired
    private DictionaryValueMapper dictionaryValueMapper;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    // Redis 存储数据字典列表默认前缀
    @Value("${DIC_PREFIX}")
    private String DIC_PREFIX;

    // Redis 存储数据字典可选列表默认前缀
    @Value("${DIC_CHECK_PREFIX}")
    private String DIC_CHECK_PREFIX;

    private Logger logger = LoggerFactory.getLogger(DictionaryValueServiceImpl.class);

    @Override
    public List<DictionaryValueVo> getDicValueList(DicValuePageConditionDto dto) {
        // 执行查询
        List<DictionaryValueVo> voList = dictionaryValueMapper.selectAllByPageCondition(dto);
        // 判断如果查询失败则抛出异常
        if (voList == null) {
            throw new DictionaryException(ErrorResultEnum.DIC_VALUE_SELECT_FAIL);
        }
        return voList;
    }

    @Override
    public Long getDicValueTotalSizeByCondition(DicValuePageConditionDto dto) {
        return dictionaryValueMapper.selectTotalSizeByCondition(dto);
    }

    @Override
    public void addDicValue(DicValueAddDto dto) {
        // 清空 Redis 缓存
        Collection<String> keys = redisCacheUtil.keys(DIC_PREFIX + "*");
        keys.addAll(redisCacheUtil.keys(DIC_CHECK_PREFIX + "*"));
        redisCacheUtil.deleteObject(keys);
        // 查询是否存在对应 typeCode 的 valueKey
        long count = dictionaryValueMapper.countByTypeIdAndValueKey(dto.getTypeId(), dto.getValueKey());
        // 如果存在则抛出异常
        if (count != 0) {
            throw new DictionaryException(ErrorResultEnum.DIC_VALUE_INSERT_FAIL_CAUSE_KEY);
        }
        // 执行添加操作
        int result = dictionaryValueMapper.insertSelective(dto);
        // 如果添加失败则抛出异常
        if (result != 1) {
            throw new DictionaryException(ErrorResultEnum.DIC_VALUE_INSERT_FAIL);
        }
    }

    @Override
    public void removeDicValueById(String id) {
        // 清空 Redis 缓存
        Collection<String> keys = redisCacheUtil.keys(DIC_PREFIX + "*");
        keys.addAll(redisCacheUtil.keys(DIC_CHECK_PREFIX + "*"));
        redisCacheUtil.deleteObject(keys);
        // 执行删除操作
        int result = dictionaryValueMapper.deleteByPrimaryKey(id);
        // 如果删除失败则抛出异常
        if (result != 1) {
            throw new DictionaryException(ErrorResultEnum.DIC_VALUE_DELETE_FAIL);
        }
    }

    @Override
    public DictionaryValue getDicValueById(String id) {
        // 执行查询操作
        DictionaryValue dictionaryValue = dictionaryValueMapper.selectByPrimaryKey(id);
        // 如果查询失败则抛出异常
        if (dictionaryValue == null) {
            throw new DictionaryException(ErrorResultEnum.DIC_VALUE_SELECT_FAIL);
        }
        return dictionaryValue;
    }

    @Override
    public void editDicValue(DictionaryValue dictionaryValue) {
        // 清空 Redis 缓存
        Collection<String> keys = redisCacheUtil.keys(DIC_PREFIX + "*");
        keys.addAll(redisCacheUtil.keys(DIC_CHECK_PREFIX + "*"));
        redisCacheUtil.deleteObject(keys);
        // 执行更新语句, 如果存在 typeCode 和 valueKey 相同地记录会抛出数据库唯一性约束异常
        int result = dictionaryValueMapper.updateByPrimaryKeySelective(dictionaryValue);
        // 如果更新失败则抛出异常
        if (result != 1) {
            throw new DictionaryException(ErrorResultEnum.DIC_VALUE_UPDATE_FAIL);
        }

    }

    @Override
    @Transactional(rollbackFor = DictionaryException.class)
    public void removeDicValueBatch(List<String> idList) {
        // 清空 Redis 缓存
        Collection<String> keys = redisCacheUtil.keys(DIC_PREFIX + "*");
        keys.addAll(redisCacheUtil.keys(DIC_CHECK_PREFIX + "*"));
        redisCacheUtil.deleteObject(keys);
        // 编译所有 id 列表依次删除
        for (String id : idList) {
            int result = dictionaryValueMapper.deleteByPrimaryKey(id);
            // 当其中有删除失败的情况则抛出异常, rollback
            if (result != 1) {
                throw new DictionaryException(ErrorResultEnum.DIC_VALUE_BATCH_DELETE_FAIL);
            }
        }
    }

    @Override
    public List<DictionaryValue> getDictionaryValueListByTypeCode(String typeCode) {
        // 执行查询操作
        List<DictionaryValue> dictionaryValueVoList = null;
        // 从 Redis 缓存服务器获取数据
        dictionaryValueVoList = redisCacheUtil.getCacheList(DIC_PREFIX + typeCode);
        if (dictionaryValueVoList == null || dictionaryValueVoList.isEmpty()) {
            synchronized (this) {
                // 从 Redis 缓存服务器获取数据
                dictionaryValueVoList = redisCacheUtil.getCacheList(DIC_PREFIX + typeCode);
                if (dictionaryValueVoList == null || dictionaryValueVoList.isEmpty()) {
                    logger.info("Redis 缓存未命中, 从数据库中查询 " + typeCode);
                    // 从数据库中获取数据
                    dictionaryValueVoList = dictionaryValueMapper.selectByDicTypeCode(typeCode);
                    redisCacheUtil.setCacheList(DIC_PREFIX + typeCode, dictionaryValueVoList);
                    // 如果查询失败则抛出异常
                    if (dictionaryValueVoList == null) {
                        throw new DictionaryException(ErrorResultEnum.DIC_VALUE_SELECT_FAIL);
                    }
                }
            }
        }
        return dictionaryValueVoList;
    }

    @Override
    public List<DicValueCheckableVo> getDicValueCheckableVoByTypeCode(String typeCode) {
        // 执行查询操作
        List<DicValueCheckableVo> dicValueCheckableVoList = null;
        // 从 Redis 缓存服务器获取数据
        dicValueCheckableVoList = redisCacheUtil.getCacheList(DIC_CHECK_PREFIX + typeCode);
        if (dicValueCheckableVoList == null || dicValueCheckableVoList.isEmpty()) {
            synchronized (this) {
                // 从 Redis 缓存服务器获取数据
                dicValueCheckableVoList = redisCacheUtil.getCacheList(DIC_CHECK_PREFIX + typeCode);
                if (dicValueCheckableVoList == null || dicValueCheckableVoList.isEmpty()) {
                    logger.info("Redis 缓存未命中, 从数据库中查询 " + typeCode);
                    // 从数据库中获取数据
                    dicValueCheckableVoList = dictionaryValueMapper.selectByDicTypeCode2Checkable(typeCode);
                    redisCacheUtil.setCacheList(DIC_CHECK_PREFIX + typeCode, dicValueCheckableVoList);
                    // 如果查询失败则抛出异常
                    if (dicValueCheckableVoList == null) {
                        throw new DictionaryException(ErrorResultEnum.DIC_VALUE_SELECT_FAIL);
                    }
                }
            }
        }
        return dicValueCheckableVoList;
    }

}
