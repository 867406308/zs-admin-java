package com.zs.modules.sys.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zs.modules.sys.demo.domain.entity.DemoEntity;
import com.zs.modules.sys.demo.mapper.DemoMapper;
import com.zs.modules.sys.demo.service.DemoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, DemoEntity> implements DemoService {

    @Override
    public List<DemoEntity> getList() {

        return baseMapper.getList();
    }
}
