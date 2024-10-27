package com.zs.modules.assets.classify.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.exception.ZsException;
import com.zs.common.core.utils.TreeUtil;
import com.zs.modules.assets.classify.domain.entity.AssetsClassifyGbEntity;
import com.zs.modules.assets.classify.domain.query.AssetsClassifyGbAddParams;
import com.zs.modules.assets.classify.domain.query.AssetsClassifyGbQueryParams;
import com.zs.modules.assets.classify.domain.vo.AssetsClassifyGbVo;
import com.zs.modules.assets.classify.mapper.AssetsClassifyGbMapper;
import com.zs.modules.assets.classify.service.IAssetsClassifyGbService;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 86740
 */
@Service
public class AssetsClassifyGbServiceImpl extends ServiceImpl<AssetsClassifyGbMapper, AssetsClassifyGbEntity>
        implements IAssetsClassifyGbService {
    @NotNull
    @Override
    public List<AssetsClassifyGbVo> getList(@NotNull AssetsClassifyGbQueryParams assetsClassifyGbQueryParams) {

        QueryWrapper<AssetsClassifyGbEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(assetsClassifyGbQueryParams.getName()), "name", assetsClassifyGbQueryParams.getName());
        queryWrapper.eq(Strings.isNotEmpty(assetsClassifyGbQueryParams.getCode()), "code", assetsClassifyGbQueryParams.getCode());

        List<AssetsClassifyGbEntity> entityList = baseMapper.selectList(queryWrapper);
        List<AssetsClassifyGbEntity> assetsClassifyGbEntityList = baseMapper.selectList(new QueryWrapper<>());

        List<AssetsClassifyGbVo> list = entityList.stream()
                .map(dept -> getTreeParent(dept, assetsClassifyGbEntityList))
                .flatMap(List::stream)
                .map(entity -> BeanUtil.copyProperties(entity, AssetsClassifyGbVo.class))
                .collect(Collectors.toList());

        return TreeUtil.build(list);
    }

    @Override
    public AssetsClassifyGbVo get(Long id) {
        return BeanUtil.copyProperties(baseMapper.selectById(id), AssetsClassifyGbVo.class);
    }

    @Override
    public void save(@NotNull AssetsClassifyGbAddParams assetsClassifyGbAddParams) {
        AssetsClassifyGbEntity assetsClassifyGbEntity = new AssetsClassifyGbEntity();
        assetsClassifyGbEntity.setPids(StrUtil.join("/", getTree(assetsClassifyGbAddParams)));
        baseMapper.insert(assetsClassifyGbEntity);
    }

    @Override
    public void update(AssetsClassifyGbAddParams assetsClassifyGbAddParams) {
        baseMapper.updateById(BeanUtil.copyProperties(assetsClassifyGbAddParams, AssetsClassifyGbEntity.class));
    }

    @Override
    public void delete(Long id) {
        // 查询是否存在子级
        Long count = baseMapper.selectCount(new QueryWrapper<AssetsClassifyGbEntity>().eq("pid", id));
        if (count > 0) {
            throw new ZsException("请先删除子级");
        }
        baseMapper.deleteById(id);
    }

    @NotNull
    public List<AssetsClassifyGbEntity> getTreeParent(@NotNull AssetsClassifyGbEntity assetsClassifyGbEntity, @NotNull List<AssetsClassifyGbEntity> deptList) {
        Map<Long, AssetsClassifyGbEntity> map = deptList.stream().collect(Collectors.toMap(AssetsClassifyGbEntity::getId, Function.identity()));
        List<AssetsClassifyGbEntity> pidList = new ArrayList<>();
        getTreePid(assetsClassifyGbEntity.getPid(), map, pidList);
        pidList.add(assetsClassifyGbEntity);
        return pidList;
    }

    public void getTreePid(Long pid, @NotNull Map<Long, AssetsClassifyGbEntity> map, @NotNull List<AssetsClassifyGbEntity> pidList) {
        AssetsClassifyGbEntity parent = map.get(pid);
        if (parent != null) {
            pidList.add(parent);
            getTreePid(parent.getPid(), map, pidList);
        }
    }


    @NotNull
    public List<Long> getTree(@NotNull AssetsClassifyGbAddParams assetsClassifyGbAddParams) {
        List<AssetsClassifyGbEntity> deptList = baseMapper.selectList(new QueryWrapper<>());
        Map<Long, AssetsClassifyGbEntity> map = new HashMap<>(deptList.size());
        for (AssetsClassifyGbEntity entity : deptList) {
            map.put(entity.getId(), entity);
        }
        List<Long> pidList = new ArrayList<>();
        getPid(assetsClassifyGbAddParams.getPid(), map, pidList);
        return pidList;
    }

    public void getPid(Long pid, @NotNull Map<Long, AssetsClassifyGbEntity> map, @NotNull List<Long> pidList) {
        AssetsClassifyGbEntity parent = map.get(pid);
        if (Objects.nonNull(parent)) {
            pidList.add(parent.getId());
            getPid(parent.getPid(), map, pidList);
        }
    }
}
