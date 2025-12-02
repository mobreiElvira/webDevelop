package com.tfzhang.aichat.model;

import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import lombok.Data;

import java.util.List;

@Data
public class ChatRoom {
    private Long id;
    private List<ChatMessage> messages;

}
