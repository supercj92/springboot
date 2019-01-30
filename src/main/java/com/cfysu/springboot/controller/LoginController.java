package com.cfysu.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author canglong
 * @Date 2019/1/28
 */
@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/login")
    public String  login(){
        return "login";
    }

    @PostMapping("/doPost")
    @ResponseBody
    public String doPost(@RequestParam String name, @RequestParam String passwd){
        log.info("{},{}", name, passwd);
        return "sucess";
    }
}
