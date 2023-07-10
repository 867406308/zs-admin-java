package com.zs.modules.sys.post.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.common.page.PageInfo;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.dept.service.ISysDeptService;
import com.zs.modules.sys.post.domain.entity.SysPostEntity;
import com.zs.modules.sys.post.domain.query.SysPostAddParams;
import com.zs.modules.sys.post.domain.query.SysPostQueryParams;
import com.zs.modules.sys.post.domain.vo.SysPostVo;
import com.zs.modules.sys.post.mapper.SysPostMapper;
import com.zs.modules.sys.post.service.ISysPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

        Map<String,Object> params = BeanUtil.beanToMap(sysPostQueryParams);
        List<String> deptList = iSysDeptService.getSubDeptIdList(sysPostQueryParams.getSysDeptId());
        params.put("deptList", deptList);
        IPage<SysPostEntity> iPage = baseMapper.page(page, params);
        List<SysPostVo> list = BeanUtil.copyToList(iPage.getRecords(), SysPostVo.class);

        return new PageResult<>(list, page.getTotal(), SysPostVo.class);
    }



    @Override
    public List<SysPostVo> getList() {
        return BeanUtil.copyToList(baseMapper.selectList(new QueryWrapper<>()), SysPostVo.class);
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
