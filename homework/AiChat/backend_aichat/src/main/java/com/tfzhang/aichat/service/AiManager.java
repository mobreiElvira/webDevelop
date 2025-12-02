package com.tfzhang.aichat.service;

import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionRequest;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionResult;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AiManager {

    @Resource
    private ArkService service;

    //传入系统prompt，用户prompt；
    public String doChat(String systemPrompt, String userPrompt) {
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content(systemPrompt).build();
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(userPrompt).build();
        messages.add(systemMessage);
        messages.add(userMessage);

//        BotChatCompletionRequest chatCompletionRequest = BotChatCompletionRequest.builder()
//                .botId("bot-20251005202857-74jzd")
//                .messages(messages)
//                .build();
//
//        BotChatCompletionResult chatCompletionResult =  service.createBotChatCompletion(chatCompletionRequest);
//        //chatCompletionResult.getChoices().forEach(choice -> System.out.println(choice.getMessage().getContent()));
//        if(chatCompletionResult.getChoices() == null || chatCompletionResult.getChoices().isEmpty()) {
////            return "没有返回结果";
//            throw new RuntimeException("AI没有返回结果");
//        }
//        return (String)chatCompletionResult.getChoices().get(0).getMessage().getContent();
        return doChat(messages);
    }

    //允许用户传入任意条消息;
    public String doChat(List<ChatMessage> messages) {
//        final List<ChatMessage> messages = new ArrayList<>();
//        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content(systemPrompt).build();
//        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(userPrompt).build();
//        messages.add(systemMessage);
//        messages.add(userMessage);

        BotChatCompletionRequest chatCompletionRequest = BotChatCompletionRequest.builder()
                .botId("bot-20251128101513-cdnfh")
                .messages(messages)
                .build();

        BotChatCompletionResult chatCompletionResult =  service.createBotChatCompletion(chatCompletionRequest);
        service.shutdownExecutor();
        //chatCompletionResult.getChoices().forEach(choice -> System.out.println(choice.getMessage().getContent()));
        if(chatCompletionResult.getChoices() == null || chatCompletionResult.getChoices().isEmpty()) {
            //return "没有返回结果";
            throw new RuntimeException("AI没有返回结果");
        }
        return (String)chatCompletionResult.getChoices().get(0).getMessage().getContent();
    }

}
