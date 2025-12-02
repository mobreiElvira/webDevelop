package com.tfzhang.aichat;

import com.tfzhang.aichat.service.AiManager;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionRequest;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionResult;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest // 修改为@SpringBootTest注解，用于测试环境加载Spring上下文
public class AiChatTest {

    @Resource
    ArkService service;
    @Autowired
    private AiManager aiManager;

    @Test
    public void doTest() {

        String systemPromt = "你是豆包，是由字节跳动开发的 AI 人工智能助手";
        String UserPrompt = "常见的十字花科植物有哪些？";
        String answer = aiManager.doChat(systemPromt, UserPrompt);
        System.out.println(answer);
    }
//        System.out.println("\n----- standard request -----");
//        final List<ChatMessage> messages = new ArrayList<>();
//        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你是豆包，是由字节跳动开发的 AI 人工智能助手").build();
//        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content("常见的十字花科植物有哪些？").build();
//        messages.add(systemMessage);
//        messages.add(userMessage);
//


//        System.out.println("\n----- streaming request -----");
//        final List<ChatMessage> streamMessages = new ArrayList<>();
//        BotChatCompletionRequest chatCompletionRequest = BotChatCompletionRequest.builder()
//                .botId("bot-20251128101513-cdnfh")
//                .messages(messages)
//                .build();
//
//        BotChatCompletionResult chatCompletionResult =  service.createBotChatCompletion(chatCompletionRequest);
//        chatCompletionResult.getChoices().forEach(choice -> System.out.println(choice.getMessage().getContent()));
//        // the references example
//        if (chatCompletionResult.getReferences() != null) {
//            chatCompletionResult.getReferences().forEach(ref -> System.out.println(ref.getUrl()));
//        }

//        BotChatCompletionRequest streamChatCompletionRequest = BotChatCompletionRequest.builder()
//                .botId("bot-20251128101513-cdnfh")
//                .messages(streamMessages)
//                .build();
//
//        service.streamBotChatCompletion(streamChatCompletionRequest)
//                .doOnError(Throwable::printStackTrace)
//                .blockingForEach(
//                        choice -> {
//                            if (choice.getReferences() != null && !choice.getReferences().isEmpty()) {
//                                choice.getReferences().forEach(ref -> System.out.println(ref.getUrl()));
//                            }
//                            if (!choice.getChoices().isEmpty()) {
//                                System.out.print(choice.getChoices().get(0).getMessage().getContent());
//                            }
//                        }
//                );
//
//        // shutdown service after all requests is finished

//    }
}
