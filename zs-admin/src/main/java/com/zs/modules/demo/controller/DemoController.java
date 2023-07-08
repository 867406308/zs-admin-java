package com.zs.modules.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController
{
    @GetMapping("hello")
    public String hello(){
        Integer nu = 0/0;
        System.out.println();
        return "123";
    }

    @GetMapping("hello1")
    public String hello1(){
        return "11111 World";
    }
}
