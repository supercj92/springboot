package com.cfysu.springboot.controller;

import com.cfysu.springboot.domain.User;
import com.cfysu.springboot.domain.UserJpaRepository;
import com.cfysu.springboot.mapper.UserMapper;
import com.cfysu.springboot.mapper.UserMapperByXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMapperByXML userMapperByXML;

    @RequestMapping("/")
    public List<User> index() {
        //return userJpaRepository.findAll();
        return userMapperByXML.getAllUser();
    }
}