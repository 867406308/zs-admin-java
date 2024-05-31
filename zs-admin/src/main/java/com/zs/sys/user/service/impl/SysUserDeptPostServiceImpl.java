package com.zs.sys.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.sys.user.domain.dto.SysUserDeptPostDTO;
import com.zs.sys.user.domain.entity.SysUserDeptPostEntity;
import com.zs.sys.user.domain.params.SysUserDeptPostAddParams;
import com.zs.sys.user.mapper.SysUserDeptPostMapper;
import com.zs.sys.user.service.ISysUserDeptPostService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 86740
 */
@Service
public class SysUserDeptPostServiceImpl extends ServiceImpl<SysUserDeptPostMapper, SysUserDeptPostEntity> implements ISysUserDeptPostService {
    @Override
    public void saveOrUpdate(Long sysUserId, List<SysUserDeptPostAddParams> sysDeptIdList) {
        // 先删除用户与部门关系
        baseMapper.delete(new QueryWrapper<SysUserDeptPostEntity>().eq("sys_user_id", sysUserId));
        // 在添加用户与部门关系
        if (!sysDeptIdList.isEmpty()) {
            for (SysUserDeptPostAddParams sysDeptId : sysDeptIdList) {
                SysUserDeptPostEntity sysUserDeptEntity = new SysUserDeptPostEntity();
                sysUserDeptEntity.setSysUserId(sysUserId);
                sysUserDeptEntity.setSysDeptId(sysDeptId.getSysDeptId());
                sysUserDeptEntity.setSysPostId(sysDeptId.getSysPostId());
                baseMapper.insert(sysUserDeptEntity);
            }
        }

    }

    @Override
    public List<SysUserDeptPostDTO> getByUserId(Long sysUserId) {
        List<SysUserDeptPostEntity> sysUserDeptPostEntityList = baseMapper.selectList(new QueryWrapper<SysUserDeptPostEntity>().eq("sys_user_id", sysUserId));
        return BeanUtil.copyToList(sysUserDeptPostEntityList, SysUserDeptPostDTO.class);
    }
}
