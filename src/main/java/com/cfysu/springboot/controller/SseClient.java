//package com.cfysu.springboot.controller;
//
//
//import io.reactivex.annotations.Nullable;
//import okhttp3.HttpUrl;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import io.reactivex.Flowable;
//
///**
// * @Author canglong
// * @Date 2023/6/15
// */
//
//public class SseClient {
//    public static void main(String[] args) throws Exception {
//
//    }
//    public static Flowable<ConversationResult> streamCall(String prompt) {
//
//        QWenConversationParam qWenConversationParam = QWenConversationParam.builder().model(Constants.DEFAULT_MODEL).prompt(prompt).stream(true).build();
//
//
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//            .url(HttpUrl.parse("https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation")
//                .newBuilder().build())
//            .addHeader("Authorization", "Bearer a2ANNwaT5faJD0tkr7vi8N1hLdhvQSYMC7B5B0C4F55211ED8DC90A192310B38C")
//            .addHeader("Accept", "text/event-stream").addHeader("X-Accel-Buffering", "no").addHeader("X-DashScope-SSE", "enable")
//            .post(RequestBody.create(qWenConversationParam.buildMessageBody("http"), MediaType.get("application/json")))
//            .build();
//
//        return Flowable.create(flowableEmitter -> {
//            RealEventSource realEventSource = new RealEventSource(request, new EventSourceListener() {
//                QWenConversationResult qWenConversationResult = null;
//
//                @Override
//                public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
//                    QWenConversationResult result = new QWenConversationResult();
//                    result.loadFromMessage("http", data);
//                    qWenConversationResult = result;
//                    qWenConversationResult.setEventType(EventType.RESULT_GENERATED.getValue());
//                    flowableEmitter.onNext(qWenConversationResult);
//                }
//
//                @Override
//                public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
//                    flowableEmitter.onError(t);
//                }
//
//                @Override
//                public void onClosed(@NotNull EventSource eventSource) {
//                    qWenConversationResult.setEventType(EventType.TASK_FINISHED.getValue());
//                    flowableEmitter.onNext(qWenConversationResult);
//                }
//
//            });
//
//            realEventSource.connect(client);
//
//        }, BackpressureStrategy.BUFFER);
//    }
//}