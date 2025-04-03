package com.cfysu.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @Author canglong
 * @Date 2023/6/19
 */
@Controller
@RequestMapping("/async")
public class AsyncController {

    private int count = 0;

    @RequestMapping("/async")
    @ResponseBody
    public DeferredResult<String> async() {
        DeferredResult<String> result = new DeferredResult<String>();

        // 异步处理请求
        new Thread(() -> {
            while (true) {
                String data = doAsyncTask();
                result.setResult(data);
            }
        }).start();

        return result;
    }

    private String doAsyncTask() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(count++);
    }

}
