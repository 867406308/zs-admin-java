package com.zs.sys.dept.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.exception.ZsException;
import com.zs.common.core.utils.TreeUtil;
import com.zs.common.mp.service.MpSysDeptService;
import com.zs.sys.dept.domain.entity.SysDeptEntity;
import com.zs.sys.dept.domain.params.SysDeptAddParams;
import com.zs.sys.dept.domain.params.SysDeptQueryParams;
import com.zs.sys.dept.domain.vo.SysDeptVo;
import com.zs.sys.dept.mapper.SysDeptMapper;
import com.zs.sys.dept.service.ISysDeptService;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 86740
 */

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDeptEntity> implements ISysDeptService, MpSysDeptService {


    @NotNull
    @Override
    public List<SysDeptVo> getTree(SysDeptQueryParams sysDeptQueryParams) {

        List<SysDeptEntity> entityList = baseMapper.getList(sysDeptQueryParams);
        List<SysDeptVo> list = BeanUtil.copyToList(entityList, SysDeptVo.class);
        return TreeUtil.build(list);


    }

    @Nullable
    @Override
    public List<SysDeptVo> getList(SysDeptQueryParams sysDeptQueryParams) {
        List<SysDeptEntity> entityList = baseMapper.getList(sysDeptQueryParams);
        return BeanUtil.copyToList(entityList, SysDeptVo.class);
    }


    @Override
    public void save(@NotNull SysDeptAddParams sysOrgAddParams) {
        SysDeptEntity sysDeptEntity = BeanUtil.copyProperties(sysOrgAddParams, SysDeptEntity.class);
        sysDeptEntity.setPids(StrUtil.join(",", getTree(sysOrgAddParams)));
        baseMapper.insert(sysDeptEntity);
    }

    @NotNull
    public List<Long> getTree(@NotNull SysDeptAddParams sysOrgAddParams) {
        List<SysDeptEntity> deptList = baseMapper.selectList(new QueryWrapper<>());
        Map<Long, SysDeptEntity> map = new HashMap<>(deptList.size());
        for (SysDeptEntity entity : deptList) {
            map.put(entity.getSysDeptId(), entity);
        }
        List<Long> pidList = new ArrayList<>();
        getPid(sysOrgAddParams.getPid(), map, pidList);
        return pidList;
    }

    public void getPid(Long pid, @NotNull Map<Long, SysDeptEntity> map, @NotNull List<Long> pidList) {
        SysDeptEntity parent = map.get(pid);
        if (Objects.nonNull(parent)) {
            pidList.add(parent.getSysDeptId());
            getPid(parent.getPid(), map, pidList);
        }
    }


    @Override
    public void update(SysDeptAddParams sysOrgAddParams) {
        baseMapper.updateById(BeanUtil.copyProperties(sysOrgAddParams, SysDeptEntity.class));
    }

    @Override
    public SysDeptVo getById(Long id) {
        return BeanUtil.copyProperties(baseMapper.getBySysDeptId(id), SysDeptVo.class);
    }

    @Override
    public void removeById(Long sysDeptId) {
        // 查询用户是否绑定部门
        // 查询是否存在子级
        Long count = baseMapper.selectCount(new QueryWrapper<SysDeptEntity>().eq("pid", sysDeptId));
        if (count > 0) {
            throw new ZsException("请先删除子级部门");
        }
        baseMapper.deleteById(sysDeptId);
    }

    @NotNull
    @Override
    public List<Long> getSubDeptIdList(Long sysDeptId) {

        if (Objects.isNull(sysDeptId)) {
            return new ArrayList<>();
        }
        List<Long> deptIdList = baseMapper.getSubDeptIdList(sysDeptId);
        deptIdList.add(sysDeptId);

        return deptIdList;
    }


    @NotNull
    @Override
    public Set<Long> getDeptAndChildrenDeptIds(Long deptId) {
        return new HashSet<>(this.getSubDeptIdList(deptId));
    }
}
