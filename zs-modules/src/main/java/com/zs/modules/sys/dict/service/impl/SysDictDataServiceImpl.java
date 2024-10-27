package com.zs.modules.sys.dict.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.constant.RedisConstants;
import com.zs.common.core.model.domain.SysDictDataDTO;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.common.core.utils.DictRedisUtil;
import com.zs.common.redis.config.RedisUtil;
import com.zs.modules.sys.dict.domain.entity.SysDictDataEntity;
import com.zs.modules.sys.dict.domain.params.SysDictDataAddParams;
import com.zs.modules.sys.dict.domain.params.SysDictDataQueryParams;
import com.zs.modules.sys.dict.domain.vo.SysDictDataVo;
import com.zs.modules.sys.dict.mapper.SysDictDataMapper;
import com.zs.modules.sys.dict.service.ISysDictDataService;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 86740
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictDataEntity> implements ISysDictDataService {

    @Resource
    private RedisUtil redisUtil;


    @NotNull
    @Override
    public PageResult<SysDictDataVo> page(@NotNull SysDictDataQueryParams sysDictDataQueryParams) {
        Page<SysDictDataEntity> page = new PageInfo<>(sysDictDataQueryParams);

        IPage<SysDictDataEntity> iPage = baseMapper.selectPage(page, getQueryWrapper(sysDictDataQueryParams));
        List<SysDictDataVo> list = BeanUtil.copyToList(iPage.getRecords(), SysDictDataVo.class);

        return new PageResult<>(list, page.getTotal(), SysDictDataVo.class);
    }

    @Nullable
    @Override
    public List<SysDictDataVo> list(@NotNull SysDictDataQueryParams sysDictDataQueryParams) {
        return BeanUtil.copyToList(baseMapper.selectList(getQueryWrapper(sysDictDataQueryParams)), SysDictDataVo.class);
    }


    @NotNull
    private QueryWrapper<SysDictDataEntity> getQueryWrapper(@NotNull SysDictDataQueryParams sysDictDataQueryParams) {
        QueryWrapper<SysDictDataEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysDictDataQueryParams.getSysDictTypeId()), "sys_dict_type_id", sysDictDataQueryParams.getSysDictTypeId());
        wrapper.like(Strings.isNotEmpty(sysDictDataQueryParams.getDictType()), "dict_type", sysDictDataQueryParams.getDictType());
        wrapper.like(Strings.isNotEmpty(sysDictDataQueryParams.getDictLabel()), "dict_label", sysDictDataQueryParams.getDictLabel());
        wrapper.like(Strings.isNotEmpty(sysDictDataQueryParams.getDictValue()), "dict_value", sysDictDataQueryParams.getDictValue());
        return wrapper;
    }

    @Override
    public SysDictDataVo getById(Long id) {
        return BeanUtil.copyProperties(baseMapper.selectById(id), SysDictDataVo.class);
    }

    @Override
    public void save(SysDictDataAddParams sysDictDataAddParams) {
        SysDictDataEntity sysDictDataEntity = BeanUtil.copyProperties(sysDictDataAddParams, SysDictDataEntity.class);
        int row = baseMapper.insert(sysDictDataEntity);
        if (row > 0) {
            saveDictDataRedis(sysDictDataEntity.getDictType());
        }
    }

    @Override
    public void update(SysDictDataAddParams sysDictDataAddParams) {
        SysDictDataEntity sysDictDataEntity = BeanUtil.copyProperties(sysDictDataAddParams, SysDictDataEntity.class);
        int row = baseMapper.updateById(sysDictDataEntity);
        if (row > 0) {
            saveDictDataRedis(sysDictDataEntity.getDictType());
        }
    }

    @Override
    public void deleteById(Long id) {
        baseMapper.deleteById(id);
        List<SysDictDataEntity> sysDictDataEntityList = baseMapper.selectList(new QueryWrapper<SysDictDataEntity>().eq("status", 1));
        if (!sysDictDataEntityList.isEmpty()) {
            redisUtil.setObject(RedisConstants.SYS_DICT_KEY + sysDictDataEntityList.get(0).getDictType(), sysDictDataEntityList);
        }
    }

    @Override
    public void batchDelById(Long[] sysDictDataIds) {
        baseMapper.deleteByIds(Arrays.asList(sysDictDataIds));

        saveCache();
    }

    @Override
    public void saveCache() {
        List<SysDictDataEntity> sysDictDataEntityList = baseMapper.selectList(new QueryWrapper<SysDictDataEntity>().eq("status", 1));
        Map<String, List<SysDictDataEntity>> sysDictDataMap = sysDictDataEntityList.stream().collect(Collectors.groupingBy(SysDictDataEntity::getDictType));
        for (Map.Entry<String, List<SysDictDataEntity>> entry : sysDictDataMap.entrySet()) {
            // 保存字典数据到redis缓存
            redisUtil.setObject(RedisConstants.SYS_DICT_KEY + entry.getKey(), entry.getValue());
        }
    }


    private void saveDictDataRedis(String dictType) {
        List<SysDictDataEntity> sysDictDataEntityList = baseMapper.selectList(new QueryWrapper<SysDictDataEntity>().eq("status", 1));
        DictRedisUtil.set(RedisConstants.SYS_DICT_KEY + dictType, BeanUtil.copyToList(sysDictDataEntityList, SysDictDataDTO.class));
    }
}
