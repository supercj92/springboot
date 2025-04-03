package com.cfysu.springboot.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.alibaba.dashscope.common.Protocol;
import com.alibaba.dashscope.conversation.qwen.QWenConversationParam;


/**
 * @Author canglong
 * @Date 2023/6/21
 */
public class QianWenUtils {
    public static InputStream invoke(String query){
        try {
            // 创建 URL 对象
            URL url = new URL("xxx");

            // 打开连接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            // 设置请求头
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "text/event-stream");
            conn.setRequestProperty("Authorization",
                "Bearer xxx");
            conn.setRequestProperty("X-Accel-Buffering", "no");
            conn.setRequestProperty("X-DashScope-SSE", "enable");
            conn.setRequestProperty("Content-Type", "application/json");

            String prompt = query;
            QWenConversationParam qWenConversationParam = QWenConversationParam.builder().model(
                "dxm_one_stop_assist_llm").prompt(prompt).stream(true).build();

            String body = qWenConversationParam.buildMessageBody(Protocol.HTTP.getValue());
            System.out.println(body);
            conn.setDoOutput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(body.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            // 发起请求
            //conn.connect();

            // 获取响应内容
            return conn.getInputStream();
            //byte[] buffer = new byte[1024];
            //int len;
            //while ((len = stream.read(buffer)) > 0) {
            //    System.out.println(new String(buffer, 0, len));
            //}

            // 关闭连接
            //conn.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
