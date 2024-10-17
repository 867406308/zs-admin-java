package com.zs.modules.sys.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.modules.sys.demo.domain.entity.DemoEntity;

import java.util.List;


public interface DemoService extends IService<DemoEntity> {

    List<DemoEntity> getList();
}
