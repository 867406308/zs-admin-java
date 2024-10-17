package com.zs.modules.sys.post.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.exception.ZsException;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.modules.sys.dept.service.ISysDeptService;
import com.zs.modules.sys.post.domain.entity.SysPostEntity;
import com.zs.modules.sys.post.domain.params.SysPostAddParams;
import com.zs.modules.sys.post.domain.params.SysPostQueryParams;
import com.zs.modules.sys.post.domain.vo.SysPostVo;
import com.zs.modules.sys.post.mapper.SysPostMapper;
import com.zs.modules.sys.post.service.ISysPostService;
import com.zs.modules.sys.user.service.ISysUserDeptPostService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    @Resource
    private ISysUserDeptPostService iSysUserDeptPostService;

    @NotNull
    @Override
    public PageResult<SysPostVo> page(@NotNull SysPostQueryParams sysPostQueryParams) {
        Page<SysPostEntity> page = new PageInfo<>(sysPostQueryParams);

        List<Long> deptList = iSysDeptService.getSubDeptIdList(sysPostQueryParams.getSysDeptId());

        QueryWrapper<SysPostEntity> queryWrapper = getSysPostEntityQueryWrapper(sysPostQueryParams, deptList);
        IPage<SysPostEntity> iPage = baseMapper.selectPage(page, queryWrapper);

        page.getRecords().forEach(item -> {
            item.setDeptName(iSysDeptService.getBySysDeptId(item.getSysDeptId()));
        });
        List<SysPostVo> list = BeanUtil.copyToList(iPage.getRecords(), SysPostVo.class);

        return new PageResult<>(list, page.getTotal(), SysPostVo.class);
    }

    private QueryWrapper<SysPostEntity> getSysPostEntityQueryWrapper(SysPostQueryParams sysPostQueryParams, List<Long> deptList) {
        QueryWrapper<SysPostEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(Strings.isNotEmpty(sysPostQueryParams.getPostName()), "post_name", sysPostQueryParams.getPostName());
        queryWrapper.eq(Objects.nonNull(sysPostQueryParams.getStatus()), "status", sysPostQueryParams.getStatus());
        queryWrapper.in(!deptList.isEmpty(),"sys_dept_id", deptList);
        return queryWrapper;
    }


    @Nullable
    @Override
    public List<SysPostVo> getList(@NotNull SysPostQueryParams sysPostQueryParams) {

        List<Long> deptList = iSysDeptService.getSubDeptIdList(sysPostQueryParams.getSysDeptId());
        Map<String, Object> params = BeanUtil.beanToMap(sysPostQueryParams);
        params.put("status", 1);
        params.put("deptList", deptList);

        return BeanUtil.copyToList(baseMapper.getList(params), SysPostVo.class);
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

    @Override
    public void batchDelById(@NotNull Long[] sysPostIds) {
        for (Long sysPostId : sysPostIds) {
            Long num = iSysUserDeptPostService.getByPostId(sysPostId);
            if (num > 0) {
                throw new ZsException("该岗位已有用户使用，不得删除。");
            }
        }
        this.baseMapper.deleteByIds(Arrays.asList(sysPostIds));
    }

    @Override
    public void delById(Long sysPostId) {
        Long num = iSysUserDeptPostService.getByPostId(sysPostId);
        if (num > 0) {
             throw new ZsException("该岗位已有用户使用，不得删除。");
        }
        this.baseMapper.deleteById(sysPostId);
    }
}
