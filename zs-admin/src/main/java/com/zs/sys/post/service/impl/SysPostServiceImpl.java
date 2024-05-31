package com.zs.sys.post.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.sys.dept.service.ISysDeptService;
import com.zs.sys.post.domain.entity.SysPostEntity;
import com.zs.sys.post.domain.params.SysPostAddParams;
import com.zs.sys.post.domain.params.SysPostQueryParams;
import com.zs.sys.post.domain.vo.SysPostVo;
import com.zs.sys.post.mapper.SysPostMapper;
import com.zs.sys.post.service.ISysPostService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 86740
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPostEntity> implements ISysPostService {
    @Resource
    private ISysDeptService iSysDeptService;

    @Override
    public PageResult<SysPostVo> page(SysPostQueryParams sysPostQueryParams) {
        Page<SysPostEntity> page = new PageInfo<>(sysPostQueryParams);

        Map<String, Object> params = BeanUtil.beanToMap(sysPostQueryParams);
        List<Long> deptList = iSysDeptService.getSubDeptIdList(sysPostQueryParams.getSysDeptId());
        params.put("deptList", deptList);
        IPage<SysPostEntity> iPage = baseMapper.page(page, params);
        List<SysPostVo> list = BeanUtil.copyToList(iPage.getRecords(), SysPostVo.class);

        return new PageResult<>(list, page.getTotal(), SysPostVo.class);
    }


    @Override
    public List<SysPostVo> getList(SysPostQueryParams sysPostQueryParams) {
        QueryWrapper<SysPostEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(sysPostQueryParams.getSysDeptId()), "sys_dept_id", sysPostQueryParams.getSysDeptId());
        queryWrapper.eq("status", 1);
        queryWrapper.orderByAsc("sort");
        return BeanUtil.copyToList(baseMapper.selectList(queryWrapper), SysPostVo.class);
    }

    @Override
    public void save(SysPostAddParams sysPostAddParams) {
        baseMapper.insert(BeanUtil.copyProperties(sysPostAddParams, SysPostEntity.class));
    }

    @Override
    public void update(SysPostAddParams sysPostAddParams) {
        baseMapper.updateById(BeanUtil.copyProperties(sysPostAddParams, SysPostEntity.class));
    }

    @Override
    public SysPostVo getById(Long id) {
        return BeanUtil.copyProperties(baseMapper.selectById(id), SysPostVo.class);
    }
}
