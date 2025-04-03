package com.cfysu.springboot.controller;

import com.cfysu.springboot.domain.User;
import com.cfysu.springboot.domain.UserJpaRepository;
import com.cfysu.springboot.mapper.UserMapper;
import com.cfysu.springboot.mapper.UserMapperByXML;
import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class UserRestController {

    //@Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMapperByXML userMapperByXML;

    @Timed
    @RequestMapping(value = "/index.htm",produces = "application/xml;charset=UTF-8")
    public List<User> index() {
        return userJpaRepository.findFirst10ByUserName("lcj");
        //return userMapper.getAll();
        //return userMapperByXML.getAllUser();
    }

    @RequestMapping("/value")
    public Long value(@RequestParam(value = "sellerId", defaultValue = "1") Long sellerId){
        System.out.println(sellerId instanceof Long);
        return sellerId;
    }

    @RequestMapping("/shift")
    public String shift(){
        StringBuilder sbd = new StringBuilder();
        sbd.append(1).append("</br>");
        sbd.append(2);
        return sbd.toString();
    }

    @GetMapping("/user")
    public User getUser(){
        System.out.println("user");
        return new User();
    }

    @GetMapping("/httpHeader")
    public void getHttpHeader(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println(key + "=" + value);
        }
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getHeader("X-FORWARDED-FOR"));
    }
}