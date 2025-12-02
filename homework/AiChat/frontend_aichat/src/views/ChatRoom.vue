<template>
  <div class="chat-room">
    <!-- 侧边栏 - 历史房间 -->
    <div class="sidebar">
      <h3>历史房间</h3>
      <a-button type="dashed" block style="margin-bottom: 12px" @click="createNewRoom">
        ＋ 新建房间
      </a-button>

      <div class="chat-history">
        <!-- 加载中状态 -->
        <div v-if="loadingRooms" class="loading-rooms">加载中...</div>
        
        <!-- 历史房间列表 -->
        <div v-else-if="chatHistory.length > 0" class="history-list">
          <div
            v-for="room in chatHistory"
            :key="room.id"
            class="history-item"
            :class="{ active: roomId === room.id }"
            @click="switchRoom(room.id)"
          >
            房间 {{ room.id }}
          </div>
        </div>
        
        <!-- 空状态 -->
        <div v-else class="empty-history">
          <div class="empty-icon">📜</div>
          <div class="empty-text">暂无历史房间</div>
          <div class="empty-tip">点击上方"新建房间"开始游戏吧～</div>
        </div>
      </div>
    </div>

    <!-- 主内容区 -->
    <div class="main-content">
      <div class="header">
        <h2>AI 脑筋急转弯</h2>
        <!-- 无 roomId 时不显示房间号 -->
        <div class="room-info" v-if="roomId">房间号: {{ roomId }}</div>
      </div>

      <!-- 消息容器 -->
      <div class="messages-container" ref="messagesContainer">
        <!-- 加载中状态 -->
        <div v-if="loadingMessages" class="loading-messages">加载房间记录中...</div>
        
        <!-- 无消息提示（区分“无roomId”和“有roomId但无消息”） -->
        <div v-if="!loading && !loadingMessages">
          <div v-if="!roomId" class="empty-messages">
            <div class="empty-messages-icon">🎮</div>
            <div class="empty-messages-text">点击"新建房间"开启脑筋急转弯～</div>
          </div>
          <div v-else-if="messages.length === 0" class="empty-messages">
            <div class="empty-messages-icon">📭</div>
            <div class="empty-messages-text">该房间暂无消息，点击"开始游戏"启动～</div>
          </div>
        </div>

        <!-- 消息列表 -->
        <div
          v-for="(message, index) in messages"
          :key="index"
          :class="['message-wrapper', message.isUser ? 'user-message' : 'ai-message']"
        >
          <div class="avatar">
            <span v-if="message.isUser">👤</span>
            <span v-else>🤖</span>
          </div>
          <div class="message-bubble">
            {{ message.content }}
          </div>
        </div>

        <!-- AI 加载中 -->
        <div v-if="loading" class="message-wrapper ai-message">
          <div class="avatar">🤖</div>
          <div class="message-bubble loading">
            <span class="dot"></span>
            <span class="dot"></span>
            <span class="dot"></span>
          </div>
        </div>
      </div>

      <!-- 输入区域 -->
      <div class="input-area">
        <div class="action-buttons">
          <!-- 无 roomId 时禁用“开始游戏”，仅允许新建房间 -->
          <a-button type="primary" :disabled="!roomId || gameStarted || gameEnded || loading" @click="startGame">
            开始游戏
          </a-button>
          <a-button danger :disabled="!roomId || !gameStarted || gameEnded || loading" @click="endGame">
            结束游戏
          </a-button>
        </div>

        <div class="input-group">
          <!-- 修复：正确绑定 ref、v-model 和事件 -->
          <a-input
            ref="inputRef"
            v-model:value="userInput"
            placeholder="请输入答案或提问..."
            @pressEnter="handleEnterPress"
            :disabled="!roomId || !gameStarted || gameEnded || loading"
            size="large"
            max-length="500"
          />
          <a-button
            type="primary"
            @click="handleSendMessage"
            :loading="loading"
            :disabled="!roomId || !gameStarted || gameEnded || loading || !userInput.trim()"
            size="large"
          >
            发送
          </a-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, nextTick, onMounted, watch } from 'vue'
import { Button, Input, message } from 'ant-design-vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

// 组件别名
const AButton = Button
const AInput = Input

// 路由相关
const route = useRoute()
const router = useRouter()

// -------------------------- API 配置（与后端接口严格匹配）--------------------------
const BASE_URL = 'http://localhost:8080'

const apiClient = axios.create({
  baseURL: BASE_URL,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 与后端 ChatRoom 实体类匹配（后端返回的房间列表格式）
export interface ChatRoom {
  id: number
  [key: string]: any
}

// 严格匹配后端接口：roomId（路径参数）+ userPrompt（查询参数）
const sendMessageApi = async (roomId: number, userPrompt: string): Promise<string> => {
  try {
    console.log(`调用接口：/${roomId}/chat?userPrompt=${encodeURIComponent(userPrompt)}`)
    const response = await apiClient.post(
      `/${roomId}/chat`, // roomId 拼接到 URL 路径（@PathVariable）
      null, // 无请求体（后端不需要）
      {
        params: { userPrompt } // userPrompt 作为查询参数（@RequestParam）
      }
    )
    console.log('接口返回：', response.data)
    // 确保返回值是字符串（后端返回格式校验）
    return typeof response.data === 'string' ? response.data : JSON.stringify(response.data)
  } catch (error) {
    console.error('发送消息接口调用失败:', error)
    throw error // 抛出错误让上层处理
  }
}

// 房间列表接口（GET /rooms）
const getChatRoomList = async (): Promise<{ id: number; messages: any[] }[]> => {
  try {
    const response = await apiClient.get('/rooms')
    // 确保返回值是数组（后端返回格式校验）
    return Array.isArray(response.data) ? response.data : []
  } catch (error) {
    console.error('获取房间列表接口调用失败:', error)
    throw error
  }
}

// -------------------------- 类型定义 --------------------------
interface BackendMessage {
  role: 'user' | 'assistant'
  content: string
  name: string | null
  reasoning_content: string | null
  function_call: any | null
  tool_calls: any | null
  tool_call_id: any | null
}

interface BackendChatRoom {
  id: number
  messages: BackendMessage[]
}

interface FrontendMessage extends Omit<BackendMessage, 'role'> {
  isUser: boolean
}

// -------------------------- 状态管理 --------------------------
let roomId = ref<number>(0) // 0 表示无房间，>0 表示有具体房间ID
const messages = ref<FrontendMessage[]>([])
const userInput = ref('')
const loading = ref(false) // 发送/开始/结束游戏的加载状态
const loadingRooms = ref(false) // 加载房间列表的状态
const loadingMessages = ref(false) // 加载消息的状态
const gameStarted = ref(false)
const gameEnded = ref(false)
const chatHistory = ref<BackendChatRoom[]>([])
const messagesContainer = ref<HTMLElement | null>(null)
const inputRef = ref<any>(null) // 输入框 ref 引用

// -------------------------- 初始化逻辑 --------------------------
// 从路由参数获取 roomId
const initRoomIdFromRoute = () => {
  const routeId = Number(route.params.roomId)
  return routeId && !isNaN(routeId) ? routeId : 0
}

// 初始化路由中的 roomId 和房间列表
onMounted(() => {
  const routeRoomId = initRoomIdFromRoute()
  if (routeRoomId) {
    roomId.value = routeRoomId
  }
  initChatRooms() // 加载历史房间列表
})

// 监听 roomId 变化：切换房间/新建房间时触发
watch(roomId, (newRoomId) => {
  if (newRoomId) {
    // 有房间ID：跳转至 /chat/:roomId，加载对应消息
    router.push(`/chat/${newRoomId}`).catch(() => {})
    loadRoomMessages(newRoomId)
  } else {
    // 无房间ID：跳转至 /chat，清空消息和游戏状态
    router.push('/chat').catch(() => {})
    messages.value = []
    gameStarted.value = false
    gameEnded.value = false
  }
}, { immediate: true })

// -------------------------- 工具函数 --------------------------
// 滚动到底部
const scrollToBottom = () => {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  })
}

// 加载历史房间列表（所有有效房间）
const initChatRooms = async () => {
  loadingRooms.value = true
  try {
    const rooms = await getChatRoomList()
    // 转换为前端需要的 BackendChatRoom 格式
    chatHistory.value = rooms.map(room => ({
      id: room.id,
      messages: room.messages || [] // 兼容后端返回无 messages 字段的情况
    }))
  } catch (error) {
    message.error('获取历史房间失败，请刷新重试')
    console.error('获取房间列表失败:', error)
    chatHistory.value = []
  } finally {
    loadingRooms.value = false
  }
}

// 加载指定房间的消息
const loadRoomMessages = (targetRoomId: number) => {
  loadingMessages.value = true
  try {
    const targetRoom = chatHistory.value.find(room => room.id === targetRoomId)
    if (targetRoom) {
      // 转换后端消息格式为前端格式
      messages.value = targetRoom.messages.map(backendMsg => ({
        content: backendMsg.content || '',
        name: backendMsg.name || null,
        reasoning_content: backendMsg.reasoning_content || null,
        function_call: backendMsg.function_call || null,
        tool_calls: backendMsg.tool_calls || null,
        tool_call_id: backendMsg.tool_call_id || null,
        isUser: backendMsg.role === 'user'
      }))
      // 更新游戏状态
      gameStarted.value = targetRoom.messages.length > 0
      gameEnded.value = targetRoom.messages.some(msg => 
        msg.content?.includes('游戏已结束') || msg.content?.includes('游戏结束')
      )
    } else {
      messages.value = []
      gameStarted.value = false
      gameEnded.value = false
    }
  } catch (error) {
    console.error('加载房间消息失败:', error)
    messages.value = []
    gameStarted.value = false
    gameEnded.value = false
  } finally {
    loadingMessages.value = false
    scrollToBottom()
  }
}

// -------------------------- 核心功能函数 --------------------------
// 新建房间
const createNewRoom = () => {
  if (loading.value) return // 加载中不允许新建
  const newRoomId = Date.now() // 唯一房间ID（时间戳）
  // 添加到历史房间列表（空消息）
  chatHistory.value.push({ id: newRoomId, messages: [] })
  // 切换到新房间
  roomId.value = newRoomId
  userInput.value = ''
  message.success(`新房间创建成功（房间号: ${newRoomId}）`)
}

// 切换房间
const switchRoom = (newRoomId: number) => {
  if (newRoomId === roomId.value || loading.value) return
  roomId.value = newRoomId
  userInput.value = ''
  loading.value = false
}

// 开始游戏
const startGame = async () => {
  // 前置校验
  if (!roomId.value || gameStarted.value || gameEnded.value || loading.value) return

  loading.value = true
  try {
    // 调用接口发送"开始"指令
    const aiResponse = await sendMessageApi(roomId.value, '开始')
    
    // 构造前后端消息
    const userStartMsg: FrontendMessage = {
      content: '开始',
      isUser: true,
      name: null,
      reasoning_content: null,
      function_call: null,
      tool_calls: null,
      tool_call_id: null
    }
    const aiResponseMsg: FrontendMessage = {
      content: aiResponse,
      isUser: false,
      name: null,
      reasoning_content: null,
      function_call: null,
      tool_calls: null,
      tool_call_id: null
    }
    
    // 更新前端消息列表
    messages.value = [userStartMsg, aiResponseMsg]
    
    // 同步更新历史列表中的房间消息
    const targetRoomIndex = chatHistory.value.findIndex(room => room.id === roomId.value)
    if (targetRoomIndex > -1) {
      chatHistory.value[targetRoomIndex].messages = [
        { ...userStartMsg, role: 'user' as const },
        { ...aiResponseMsg, role: 'assistant' as const }
      ]
    }
    
    // 更新游戏状态
    gameStarted.value = true
    gameEnded.value = aiResponse.includes('游戏已结束') || aiResponse.includes('游戏结束')
    
    message.success('游戏开始成功！')
    scrollToBottom()
  } catch (error) {
    message.error('游戏启动失败，请重试')
    console.error('开始游戏失败:', error)
  } finally {
    loading.value = false
  }
}

// 回车事件处理（简化版，直接使用响应式数据）
const handleEnterPress = () => {
  const content = userInput.value.trim()
  if (!content) {
    message.warning('请输入内容后再发送')
    return
  }
  handleSendMessage()
}

// 发送消息（核心功能）
const handleSendMessage = async () => {
  const content = userInput.value.trim() // 直接从响应式数据获取
  
  // 前置校验（确保所有条件满足）
  if (!roomId.value) {
    message.warning('请先新建房间再发送消息')
    return
  }
  if (!gameStarted.value) {
    message.warning('请先点击"开始游戏"启动房间')
    return
  }
  if (gameEnded.value) {
    message.warning('游戏已结束，可新建房间重新开始')
    return
  }
  if (!content) {
    message.warning('请输入内容后再发送')
    return
  }
  if (loading.value) {
    return
  }

  // 1. 本地预览用户消息
  const userMsg: FrontendMessage = {
    content,
    isUser: true,
    name: null,
    reasoning_content: null,
    function_call: null,
    tool_calls: null,
    tool_call_id: null
  }
  messages.value.push(userMsg)
  userInput.value = '' // 清空输入框
  scrollToBottom()

  loading.value = true
  try {
    // 2. 调用接口发送消息（严格匹配后端格式）
    const aiResponse = await sendMessageApi(roomId.value, content)
    
    // 3. 添加AI回复
    const aiMsg: FrontendMessage = {
      content: aiResponse,
      isUser: false,
      name: null,
      reasoning_content: null,
      function_call: null,
      tool_calls: null,
      tool_call_id: null
    }
    messages.value.push(aiMsg)
    
    // 4. 同步更新历史列表中的房间消息
    const targetRoomIndex = chatHistory.value.findIndex(room => room.id === roomId.value)
    if (targetRoomIndex > -1) {
      chatHistory.value[targetRoomIndex].messages.push(
        { ...userMsg, role: 'user' as const },
        { ...aiMsg, role: 'assistant' as const }
      )
    }
    
    // 5. 检查游戏是否结束
    if (aiResponse.includes('游戏已结束') || aiResponse.includes('游戏结束')) {
      gameEnded.value = true
      gameStarted.value = false
      message.info('游戏已结束，可新建房间重新开始')
    }
    
    scrollToBottom()
  } catch (error) {
    message.error('发送失败，请重试')
    console.error('发送消息失败:', error)
    // 失败时移除本地预览的用户消息
    if (messages.value.length > 0 && messages.value[messages.value.length - 1].isUser) {
      messages.value.pop()
    }
  } finally {
    // 无论成功失败，都重置加载状态
    loading.value = false
  }
}

// 结束游戏
const endGame = async () => {
  if (!roomId.value || !gameStarted.value || gameEnded.value || loading.value) return

  loading.value = true
  try {
    const aiResponse = await sendMessageApi(roomId.value, '结束')
    
    // 构造结束消息
    const userEndMsg: FrontendMessage = {
      content: '我要结束游戏',
      isUser: true,
      name: null,
      reasoning_content: null,
      function_call: null,
      tool_calls: null,
      tool_call_id: null
    }
    const aiEndMsg: FrontendMessage = {
      content: aiResponse,
      isUser: false,
      name: null,
      reasoning_content: null,
      function_call: null,
      tool_calls: null,
      tool_call_id: null
    }
    
    // 更新消息列表
    messages.value.push(userEndMsg, aiEndMsg)
    
    // 同步更新历史列表
    const targetRoomIndex = chatHistory.value.findIndex(room => room.id === roomId.value)
    if (targetRoomIndex > -1) {
      chatHistory.value[targetRoomIndex].messages.push(
        { ...userEndMsg, role: 'user' as const },
        { ...aiEndMsg, role: 'assistant' as const }
      )
    }
    
    // 更新游戏状态
    gameStarted.value = false
    gameEnded.value = true
    message.success('游戏已结束')
    scrollToBottom()
  } catch (error) {
    message.error('结束游戏失败，请重试')
    console.error('结束游戏失败:', error)
  } finally {
    loading.value = false
  }
}

// 监听消息变化自动滚动到底部
watch(messages, scrollToBottom, { deep: true })
</script>

<style scoped>
/* 保持原有样式不变，确保界面美观 */
.chat-room {
  display: flex;
  height: 100vh;
  width: 100%;
  overflow: hidden;
  background-color: #f5f7fa;
}

.sidebar {
  width: 260px;
  background-color: #fff;
  border-right: 1px solid #e8e8e8;
  padding: 20px;
  display: flex;
  flex-direction: column;
}

.sidebar h3 {
  margin: 0 0 16px 0;
  font-size: 18px;
  font-weight: 600;
  color: #1890ff;
}

.chat-history {
  flex: 1;
  overflow-y: auto;
  margin-top: 8px;
  padding: 8px 0;
  display: flex;
  flex-direction: column;
}

.history-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.history-item {
  padding: 12px 16px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
  font-size: 14px;
}

.history-item:hover {
  background-color: #f5f7fa;
}

.history-item.active {
  background-color: #e6f7ff;
  color: #1890ff;
  font-weight: 500;
}

.empty-history {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px 20px;
  text-align: center;
  color: #666;
  font-size: 14px;
  background-color: #fafafa;
  border-radius: 12px;
  margin: 16px 0;
}

.empty-icon {
  font-size: 48px;
  margin-bottom: 16px;
  color: #ccc;
}

.empty-text {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 8px;
  color: #333;
}

.empty-tip {
  color: #999;
  font-size: 13px;
}

.loading-rooms {
  padding: 40px 20px;
  text-align: center;
  color: #999;
  font-size: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.header h2 {
  margin: 0;
  font-size: 24px;
  color: #1f2937;
}

.room-info {
  font-size: 14px;
  color: #666;
  background-color: #e8f4f8;
  padding: 4px 12px;
  border-radius: 16px;
}

.messages-container {
  flex: 1;
  overflow-y: auto;
  background-color: #fff;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.empty-messages {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #999;
  font-size: 16px;
  gap: 16px;
}

.empty-messages-icon {
  font-size: 64px;
  color: #eee;
}

.empty-messages-text {
  color: #666;
  font-size: 18px;
}

.loading-messages {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
  font-size: 16px;
}

.message-wrapper {
  display: flex;
  margin-bottom: 16px;
  max-width: 80%;
}

.user-message {
  flex-direction: row-reverse;
  margin-left: auto;
}

.avatar {
  font-size: 24px;
  margin: 0 8px;
  display: flex;
  align-items: flex-start;
}

.message-bubble {
  padding: 12px 16px;
  border-radius: 16px;
  font-size: 14px;
  line-height: 1.5;
  word-break: break-all;
}

.user-message .message-bubble {
  background-color: #1890ff;
  color: #fff;
  border-bottom-right-radius: 4px;
}

.ai-message .message-bubble {
  background-color: #f5f7fa;
  color: #333;
  border-bottom-left-radius: 4px;
}

.loading {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 12px 20px;
}

.dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: #1890ff;
  margin: 0 4px;
  animation: bounce 1.4s infinite ease-in-out both;
}

.dot:nth-child(1) {
  animation-delay: -0.32s;
}

.dot:nth-child(2) {
  animation-delay: -0.16s;
}

@keyframes bounce {
  0%, 80%, 100% {
    transform: scale(0);
  }
  40% {
    transform: scale(1);
  }
}

.input-area {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.action-buttons {
  display: flex;
  gap: 12px;
}

.input-group {
  display: flex;
  gap: 8px;
}

.input-group .ant-input {
  flex: 1;
  border-radius: 8px !important;
}

.input-group .ant-btn {
  border-radius: 8px !important;
  padding: 0 24px;
}

@media (max-width: 768px) {
  .chat-room {
    flex-direction: column;
  }
  
  .sidebar {
    width: 100%;
    height: auto;
    border-right: none;
    border-bottom: 1px solid #e8e8e8;
  }
  
  .message-wrapper {
    max-width: 90%;
  }
  
  .empty-history {
    padding: 20px 10px;
    margin: 8px 0;
  }
  
  .empty-icon {
    font-size: 36px;
  }
}
</style>