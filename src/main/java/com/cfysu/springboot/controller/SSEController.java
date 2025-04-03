package com.cfysu.springboot.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

/**
 * @Author canglong
 * @Date 2023/6/15
 */
@RestController
public class SSEController {

    @GetMapping("/sse")
    public SseEmitter sseEmitter() {
        SseEmitter emitter = new SseEmitter();
        // 设置超时时间
        //emitter.setTimeout(60_000L);
        // 发送数据
        //一定要启动一根新线程，不然所有的响应都同步返回。
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    emitter.send(SseEmitter.event()
                        .id("_" + i)
                        .data("_Data " + i)
                        .name("_event1"));
                    //emitter.send("_Data " + i);
                    Thread.sleep(1000L);
                } catch (Exception e) {
                    emitter.completeWithError(e);
                }
            }
            // 完成
            emitter.complete();
        }).start();
        return emitter;
    }

    @GetMapping(value = "/qianwen",produces = TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<ResponseBodyEmitter> qianWen(String query) {
        ResponseBodyEmitter responseBodyEmitter = new ResponseBodyEmitter();

        new Thread(() -> {
            InputStream inputStream = QianWenUtils.invoke(query);
            send(inputStream, responseBodyEmitter);
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            responseBodyEmitter.complete();
        }).start();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/event-stream;charset=UTF-8");
        return new ResponseEntity<>(responseBodyEmitter, headers, HttpStatus.OK);
    }

    private void send(InputStream inputStream, ResponseBodyEmitter responseBodyEmitter) {
        int len;
        int line = 0;
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        char[] buffer = new char[1024];
        while (true) {
            try {
                if (!((len = reader.read(buffer)) > 0)) {break;}
                String reply = new String(buffer, 0, len);
                System.out.println("new line-->> " + line++ + ":" + reply);
                responseBodyEmitter.send(reply);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
