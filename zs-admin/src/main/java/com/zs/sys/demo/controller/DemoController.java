package com.zs.sys.demo.controller;

import com.zs.common.core.core.Result;
import com.zs.sys.demo.domain.entity.DemoEntity;
import com.zs.sys.demo.service.DemoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("system/sys/demo")
public class DemoController {

    @Resource
    private DemoService demoService;

    @RequestMapping("list")
    private Result<List<DemoEntity>> list(){
        List<DemoEntity> list = demoService.getList();
        return new Result<List<DemoEntity>>().ok(list);
    }

}
