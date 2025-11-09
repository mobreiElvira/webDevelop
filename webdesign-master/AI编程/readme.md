我正在开发一个‌ AI 脑筋急转弯网站，请你根据我的需求、业‎务流程、前端使用的技术栈、页面原型，来帮‍我到ainaojin-frontend项目文件夹中，生成一些页面。如果有必要可以删除掉项目文件夹中的一些初始文件。

需求：
1）和 AI 主持人对话
2）查看往期的对话记录

业务流程：
1）玩家进入页﻿面，点击【开始游戏】，进入聊天室页面
2）进入聊天‌室页面时，AI 会立刻给出一个招呼语（给出故事汤面‎）
3）接下来，用户可以和 AI 主持人进行对话
‍4）用户可以主动结束，也可以由 AI 主动结束游戏
5）用户可以随时查看往期的对话记录

页面：
1）页面 1(firstpage.png)：上方是标题“﻿AI 急转弯”，下方是“点击开始”按钮，用户点击开始后，进入页面 ‌2。
2）页面 2(secondpage.png)： 页面 2 是聊天界面，上方显示当前房间号；中‎间为对话区域，左侧展示 AI 的回复（需要有个头像 + 对话框），‍右侧展示我的输入（需要有个头像 + 对话框）。下方是输入框和发送消息按钮。输入框上面有“开始”和“结束”按钮。

交互逻辑：
1. 进入页面时，要生成 1 个数字房间号
2. 点击开始按钮或者输入“开始”2 个字后，调用后端，向后端发送“开始”，后端会给出 AI 的回复，要展示在聊天区域左侧，要把我每一次发送的内容展示在右侧。从上到下展示消息。并且一旦 AI 给了回复，开始按钮就要禁止点击。
3. 每次我给 AI 发送消息，都要向后端发送我输入的消息内容，并且展示我输入的消息和 AI 给出的回复。
4. 当 AI 给的回复包含了【游戏已结束】时，前端要将结束按钮置灰，禁止点击

前端使用的技术栈都已经安装到项目文件ainaojin-frontend中：
- Vue 3：适合快速开发单页面应用（遵循 setup 组合式 api 的写法）
- Ant Design Vue：主流组件库，兼容 PC 端和移动端响应式
- Vue Router：前端路由组件
- Axios：主流的请求库

请将你的代码添加到项目文件ainaojin-frontend中。

另外后端的接口信息如下：

```java
http://localhost:8080/rooms,get请求，没有参数，作用是接收房间列表；
http://localhost:8080/{roomId}/chat；post请求，通过url传递参数，需要的参数：roomId和userPrompt；
```

对应的后端java实例代码如下：

```java
@RestController
public class ChatController {

    @Resource
    private ChatService chatService;

    @PostMapping("/{roomId}/chat")
    public String doChat(@PathVariable long roomId, @RequestParam String userPrompt) {
        return chatService.doChat(roomId, userPrompt);
    }

    @GetMapping("/rooms")
    public List<ChatRoom> getChatRoomList() {
        return chatService.getChatRoomList();
    }
}
```







