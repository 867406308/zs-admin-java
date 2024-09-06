package com.zs.sys.post.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.core.exception.ZsException;
import com.zs.common.core.page.PageInfo;
import com.zs.common.core.page.PageResult;
import com.zs.sys.dept.service.ISysDeptService;
import com.zs.sys.post.domain.entity.SysPostEntity;
import com.zs.sys.post.domain.params.SysPostAddParams;
import com.zs.sys.post.domain.params.SysPostQueryParams;
import com.zs.sys.post.domain.vo.SysPostVo;
import com.zs.sys.post.mapper.SysPostMapper;
import com.zs.sys.post.service.ISysPostService;
import com.zs.sys.user.service.ISysUserDeptPostService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        Map<String, Object> params = BeanUtil.beanToMap(sysPostQueryParams);
        List<Long> deptList = iSysDeptService.getSubDeptIdList(sysPostQueryParams.getSysDeptId());
        params.put("deptList", deptList);
        IPage<SysPostEntity> iPage = baseMapper.page(page, params);
        List<SysPostVo> list = BeanUtil.copyToList(iPage.getRecords(), SysPostVo.class);

        return new PageResult<>(list, page.getTotal(), SysPostVo.class);
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
