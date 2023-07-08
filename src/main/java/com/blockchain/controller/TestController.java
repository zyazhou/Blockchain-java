package com.blockchain.controller;

import com.blockchain.common.lang.Result;
import com.blockchain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    UserService userService;
    @GetMapping("/test")
    public Result test() {
        return Result.succ( userService.list() );
    }
}