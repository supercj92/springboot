package com.cfysu.springboot.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.alibaba.dashscope.common.Protocol;
import com.alibaba.dashscope.conversation.qwen.QWenConversationParam;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.Test;

/**
 * @Author canglong
 * @Date 2023/6/15
 */
public class SSEControllerTest {

    @Test
    public void testSseEmitter() {
        try {
            // 创建 URL 对象
            URL url = new URL("http://localhost:4443/sse");

            // 打开连接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            // 设置请求头
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "text/event-stream");
            conn.setRequestProperty("Content-Type", "application/json");

            // 发起请求
            conn.connect();

            // 获取响应内容
            InputStream stream = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = stream.read(buffer)) > 0) {
                System.out.println(new String(buffer, 0, len));
            }

            // 关闭连接
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQianWen() {
        try {
            // 创建 URL 对象
            URL url = new URL("https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation");

            // 打开连接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            // 设置请求头
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "text/event-stream");
            conn.setRequestProperty("Authorization",
                "Bearer a2ANNwaT5faJD0tkr7vi8N1hLdhvQSYMC7B5B0C4F55211ED8DC90A192310B38C");
            conn.setRequestProperty("X-Accel-Buffering", "no");
            conn.setRequestProperty("X-DashScope-SSE", "enable");
            conn.setRequestProperty("Content-Type", "application/json");

            String prompt = "你是谁";
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
            InputStream stream = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = stream.read(buffer)) > 0) {
                System.out.println(new String(buffer, 0, len));
            }

            // 关闭连接
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}