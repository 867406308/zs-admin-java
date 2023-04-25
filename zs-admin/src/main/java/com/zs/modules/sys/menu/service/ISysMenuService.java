package com.zs.modules.sys.menu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.common.core.Result;
import com.zs.common.page.PageResult;
import com.zs.modules.sys.menu.domain.dto.SysMenuDTO;
import com.zs.modules.sys.menu.domain.entity.SysMenuEntity;
import com.zs.modules.sys.menu.domain.vo.SysMenuVo;

import java.util.List;
import java.util.Map;

public interface ISysMenuService extends IService<SysMenuEntity> {


    PageResult<SysMenuVo> page(Map<String, Object> params);

    List<SysMenuVo> getList();

    void save(SysMenuDTO sysMenuDTO);

    void update(SysMenuDTO sysMenuDTO);

    SysMenuVo getById(Long id);
}
