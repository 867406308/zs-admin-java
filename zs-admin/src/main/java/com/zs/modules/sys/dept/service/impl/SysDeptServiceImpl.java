package com.zs.modules.sys.dept.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.exception.ZsException;
import com.zs.common.utils.TreeUtil;
import com.zs.modules.sys.dept.mapper.SysDeptMapper;
import com.zs.modules.sys.dept.service.ISysDeptService;
import com.zs.modules.sys.dept.domain.entity.SysDeptEntity;
import com.zs.modules.sys.dept.domain.query.SysDeptAddParams;
import com.zs.modules.sys.dept.domain.vo.SysDeptVo;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 86740
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDeptEntity> implements ISysDeptService {


    @Override
    public List<SysDeptVo> getList() {
        List<SysDeptVo> list = BeanUtil.copyToList(baseMapper.selectList(new QueryWrapper<>()), SysDeptVo.class);
        return TreeUtil.build(list, 0L);
    }

    @Override
    public void save(SysDeptAddParams sysOrgAddParams) {
        SysDeptEntity sysDeptEntity = BeanUtil.copyProperties(sysOrgAddParams, SysDeptEntity.class);
        sysDeptEntity.setPids(StrUtil.join(",", getTree(sysOrgAddParams)));
        baseMapper.insert(sysDeptEntity);
    }

    public List<Long> getTree(SysDeptAddParams sysOrgAddParams){
        List<SysDeptEntity> deptList = baseMapper.selectList(new QueryWrapper<>());
        Map<Long, SysDeptEntity> map = new HashMap<>(deptList.size());
        for(SysDeptEntity entity : deptList){
            map.put(entity.getSysDeptId(), entity);
        }
        List<Long> pidList = new ArrayList<>();
        getPid(sysOrgAddParams.getPid(), map, pidList);
        return  pidList;
    }

    public void getPid(Long pid,  Map<Long, SysDeptEntity> map, List<Long> pidList ){
        SysDeptEntity parent = map.get(pid);
        if(Objects.nonNull(parent)){
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
        return BeanUtil.copyProperties(baseMapper.selectById(id), SysDeptVo.class);
    }

    @Override
    public void removeById(Long sysDeptId) {
        // 查询用户是否绑定部门
        // 查询是否存在子级
        Long count = baseMapper.selectCount(new QueryWrapper<SysDeptEntity>().eq("pid", sysDeptId));
        if(count > 0){
            throw new ZsException("请先删除子级部门");
        }
        baseMapper.deleteById(sysDeptId);
    }

    @Override
    public List<String> getSubDeptIdList(String sysDeptId) {

        if(Objects.isNull(sysDeptId)){
            return new ArrayList<>();
        }
        List<String> deptIdList = baseMapper.getSubDeptIdList(sysDeptId);
        deptIdList.add(sysDeptId);

        return deptIdList;
    }
}
