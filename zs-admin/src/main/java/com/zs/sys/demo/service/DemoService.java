package com.zs.sys.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zs.sys.demo.domain.entity.DemoEntity;

import java.util.List;


public interface DemoService extends IService<DemoEntity> {

    List<DemoEntity> getList();
}
