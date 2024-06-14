package com.chen.domain.controller;

import com.chen.domain.common.result.Result;
import com.chen.domain.common.result.Results;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    @PostMapping("login")
    public Result login(String username,String password){
        Map<String,String> map=new HashMap<>();
        map.put("token", UUID.randomUUID().toString());
        map.put("token_type","Bearer");
        return Results.success(map);
    }

    @GetMapping("info")
    public Result info(String token){
        Map<String,String> map=new HashMap<>();
        map.put("nickName","chenchenchen");
        map.put("avatar","");
        return Results.success(map);
    }
}

