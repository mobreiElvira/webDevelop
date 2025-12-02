package com.tfzhang.aichat.service.impl;

import com.tfzhang.aichat.model.ChatRoom;
import com.tfzhang.aichat.service.AiManager;
import com.tfzhang.aichat.service.ChatService;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private AiManager aiManager;

    // 一个map结构，根据rommId, 存储已经发生的对话
    //存储历史对话；
    //每次对话需要添加对话；
    final Map<Long, List<ChatMessage>> chatHistories = new HashMap<>();

     @Override
     public String doChat(Long roomId, String userPrompt) {

//         String systemPrompt = "你是一位脑筋急转弯游戏主持人，我们将进行一个“是非问答”推理游戏。\n" +
//                 "\n" +
//                 "游戏规则如下：\n" +
//                 "\n" +
//                 "当我说“开始”时，你要随机出一道脑筋急转弯题目（题干简短、有趣、但需要逻辑推理或反向思考）。\n" +
//                 "\n" +
//                 "出题后，你只负责回答我的提问，每次只能回答以下三种之一：\n" +
//                 "\n" +
//                 "是\n" +
//                 "\n" +
//                 "否\n" +
//                 "\n" +
//                 "与此无关\n" +
//                 "\n" +
//                 "在合适的时候，你可以适当引导我，比如说“你离真相更近了”或“你可能忽略了某个细节”。\n" +
//                 "\n" +
//                 "游戏结束条件（满足任一即可）：\n" +
//                 "\n" +
//                 "我说出“不想玩了”、“告诉我答案”、“揭晓答案”等类似表达；\n" +
//                 "\n" +
//                 "我已经基本推理出真相、还原了故事，或所有关键问题都被询问到；\n" +
//                 "\n" +
//                 "我输入“退出”；\n" +
//                 "\n" +
//                 "已经问了 10 个问题，但我仍然没有接近真相或关键线索。\n" +
//                 "\n" +
//                 "结束时你的任务：\n" +
//                 "\n" +
//                 "输出“游戏结束”，并给出本题的正确答案或“汤底”（即故事的完整解释）。\n" +
//                 "\n" +
//                 "如果我表现得不错，可以适当给一句点评或鼓励。\n" +
//                 "\n" +
//                 "准备好后，当我输入“开始”，游戏正式开始。\n" +
//                 "\n" +
//                 "示例：\n" +
//                 "用户： 开始\n" +
//                 " AI（主持人）：\n" +
//                 " 题目：\n" +
//                 " 一个男人走进餐厅，点了一碗海龟汤。喝了一口后，他立刻离开餐厅并自杀了。为什么？\n" +
//                 " （你可以开始问问题了，我只会回答“是”、“否”或“与此无关”。）\n" +
//                 "\n" +
//                 "用户： 他是因为汤里真的有海龟肉吗？\n" +
//                 " AI： 是。\n" +
//                 "\n" +
//                 "用户： 他以前吃过这种汤吗？\n" +
//                 " AI： 是。\n" +
//                 "\n" +
//                 "用户： 他以前吃的不是海龟？\n" +
//                 " AI： 是。你越来越接近真相了。\n" +
//                 "\n" +
//                 "用户： 他以前吃的是人肉？\n" +
//                 " AI： 是。\n" +
//                 "\n" +
//                 "AI（主持人判断游戏应结束）：\n" +
//                 " 游戏结束。\n" +
//                 " 汤底：\n" +
//                 " 这个男人曾经在海难中和幸存者一起被困在荒岛。那时同伴煮的“海龟肉”其实是人肉。多年后他在餐厅里第一次真正喝到海龟汤，发现味道不同，从而意识到当年的真相，愧疚自杀。\n" +
//                 " 你的推理很棒，只用了 4 个问题！";

         // 1.需要根据roomId, 从map中获取历史对话,未找到历史记录，构建对话列表：
         List<ChatMessage> messages = new ArrayList<>();
//         final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content(systemPrompt).build();
         final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(userPrompt).build();

         // 1.1 如果是第一次开始要新建一个map，并将roonId放进去
         if(!chatHistories.containsKey(roomId) && userPrompt.equals("开始")) {
             chatHistories.put(roomId, messages);
//             messages.add(systemMessage);
         }else{
             messages = chatHistories.get(roomId);
         }

         // 1.2 如果不是第一次开始，根据roomId从map中获取历史对话
         messages.add(userMessage);

         // 2.将对话列表发给ai，doChat:
         String answer = aiManager.doChat(messages);
         final  ChatMessage answerMessage = ChatMessage.builder().role(ChatMessageRole.ASSISTANT).content(answer).build();
         messages.add(answerMessage);

         if(answer.contains("游戏结束")){
             chatHistories.remove(roomId);
         }

         // 3.返回结果
         return answer;
    }

    @Override
    public List<ChatRoom> getChatRoomList() {
        List<ChatRoom> chatRoomList = new ArrayList<>();
        for(Map.Entry<Long, List<ChatMessage>> entry : chatHistories.entrySet()){
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setId(entry.getKey());
            List<ChatMessage> messages = entry.getValue();
            chatRoom.setMessages(messages);
            chatRoomList.add(chatRoom);
        }
        return chatRoomList;
    }
}
