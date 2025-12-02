package com.tfzhang.aichat.service;

import com.tfzhang.aichat.model.ChatRoom;

import java.util.List;

public interface ChatService {
    //用户提供聊天室房间，以及提示词；
    // 通过房间号，与特定用户对话
    String doChat(Long roomId, String userPrompt);

    //返回对话列表；
    // 获取所有的聊天房间对话
    List<ChatRoom> getChatRoomList();
}
