package com.diandian.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/toLogin")
    public String toLogin(/*SysUser user*/) {
    	System.out.println("tologin");
    	return "tologin";
    }

}
