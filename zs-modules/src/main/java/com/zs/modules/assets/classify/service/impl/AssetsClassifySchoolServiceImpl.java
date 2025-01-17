package com.zs.modules.assets.classify.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.exception.ZsException;
import com.zs.common.core.utils.TreeUtil;
import com.zs.modules.assets.classify.domain.entity.AssetsClassifySchoolEntity;
import com.zs.modules.assets.classify.domain.query.AssetsClassifySchoolAddParams;
import com.zs.modules.assets.classify.domain.query.AssetsClassifySchoolQueryParams;
import com.zs.modules.assets.classify.domain.vo.AssetsClassifySchoolVo;
import com.zs.modules.assets.classify.mapper.AssetsClassifySchoolMapper;
import com.zs.modules.assets.classify.service.IAssetsClassifySchoolService;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 86740
 */
@Service
public class AssetsClassifySchoolServiceImpl extends ServiceImpl<AssetsClassifySchoolMapper, AssetsClassifySchoolEntity>
        implements IAssetsClassifySchoolService {
    @NotNull
    @Override
    public List<AssetsClassifySchoolVo> getList(AssetsClassifySchoolQueryParams assetsClassifySchoolQueryParams) {


        List<AssetsClassifySchoolEntity> entityList = baseMapper.getList(assetsClassifySchoolQueryParams);

        List<AssetsClassifySchoolEntity> assetsClassifySchoolEntityList = baseMapper.selectList(new QueryWrapper<>());

        List<AssetsClassifySchoolVo> list = entityList.stream()
                .map(dept -> getTreeParent(dept, assetsClassifySchoolEntityList))
                .flatMap(List::stream)
                .map(entity -> BeanUtil.copyProperties(entity, AssetsClassifySchoolVo.class))
                .collect(Collectors.toList());

        return TreeUtil.build(list);
    }

    @Override
    public AssetsClassifySchoolVo get(Long id) {
        return BeanUtil.copyProperties(baseMapper.getById(id), AssetsClassifySchoolVo.class);
    }

    @Override
    public void save(@NotNull AssetsClassifySchoolAddParams assetsClassifySchoolAddParams) {
        AssetsClassifySchoolEntity assetsClassifySchoolEntity = new AssetsClassifySchoolEntity();
        assetsClassifySchoolEntity.setPids(StrUtil.join(",", getTree(assetsClassifySchoolAddParams)));
        baseMapper.insert(assetsClassifySchoolEntity);
    }

    @Override
    public void update(AssetsClassifySchoolAddParams assetsClassifySchoolAddParams) {
        baseMapper.updateById(BeanUtil.copyProperties(assetsClassifySchoolAddParams, AssetsClassifySchoolEntity.class));
    }

    @Override
    public void delete(Long id) {
        // 查询是否存在子级
        Long count = baseMapper.selectCount(new QueryWrapper<AssetsClassifySchoolEntity>().eq("pid", id));
        if (count > 0) {
            throw new ZsException("请先删除子级部门");
        }
        baseMapper.deleteById(id);
    }

    @NotNull
    public List<AssetsClassifySchoolEntity> getTreeParent(@NotNull AssetsClassifySchoolEntity assetsClassifySchoolEntity, @NotNull List<AssetsClassifySchoolEntity> deptList) {
        Map<Long, AssetsClassifySchoolEntity> map = deptList.stream().collect(Collectors.toMap(AssetsClassifySchoolEntity::getId, Function.identity()));
        List<AssetsClassifySchoolEntity> pidList = new ArrayList<>();
        getTreePid(assetsClassifySchoolEntity.getPid(), map, pidList);
        pidList.add(assetsClassifySchoolEntity);
        return pidList;
    }

    public void getTreePid(Long pid, @NotNull Map<Long, AssetsClassifySchoolEntity> map, @NotNull List<AssetsClassifySchoolEntity> pidList) {
        AssetsClassifySchoolEntity parent = map.get(pid);
        if (parent != null) {
            pidList.add(parent);
            getTreePid(parent.getPid(), map, pidList);
        }
    }


    @NotNull
    public List<Long> getTree(@NotNull AssetsClassifySchoolAddParams assetsClassifySchoolAddParams) {
        List<AssetsClassifySchoolEntity> deptList = baseMapper.selectList(new QueryWrapper<>());
        Map<Long, AssetsClassifySchoolEntity> map = new HashMap<>(deptList.size());
        for (AssetsClassifySchoolEntity entity : deptList) {
            map.put(entity.getId(), entity);
        }
        List<Long> pidList = new ArrayList<>();
        getPid(assetsClassifySchoolAddParams.getPid(), map, pidList);
        return pidList;
    }

    public void getPid(Long pid, @NotNull Map<Long, AssetsClassifySchoolEntity> map, @NotNull List<Long> pidList) {
        AssetsClassifySchoolEntity parent = map.get(pid);
        if (Objects.nonNull(parent)) {
            pidList.add(parent.getId());
            getPid(parent.getPid(), map, pidList);
        }
    }
}
