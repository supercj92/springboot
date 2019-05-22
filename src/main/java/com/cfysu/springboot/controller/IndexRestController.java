package com.cfysu.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.cfysu.springboot.domain.ConfigDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author canglong
 * @Date 2019/1/31
 */
@RestController
public class IndexRestController {

    @Autowired
    private ConfigDO configDO;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/config")
    public ConfigDO getConfigDO(){
        System.out.println(JSONObject.toJSONString(configDO));
        return configDO;
    }
}
