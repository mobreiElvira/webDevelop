<template>
  <div class="room-history">
    <div class="container">
      <div class="header">
        <h2>历史对话记录</h2>
        <a-button @click="goBack">返回首页</a-button>
      </div>

      <a-spin :spinning="loading" size="large">
        <div v-if="!loading && rooms.length === 0" class="empty-state">
          <div class="empty-icon">📝</div>
          <p>暂无对话记录</p>
          <a-button type="primary" @click="goBack">开始新游戏</a-button>
        </div>

        <div v-else class="rooms-grid">
          <div
            v-for="room in rooms"
            :key="room.id"
            class="room-card"
            @click="viewRoom(room.id)"
          >
            <div class="room-header">
              <span class="room-icon">🎮</span>
              <span class="room-id">房间 {{ room.id }}</span>
            </div>
            <div class="room-info">
              <p v-if="room.createTime">创建时间: {{ formatDate(room.createTime) }}</p>
              <p v-if="room.messageCount">消息数: {{ room.messageCount }}</p>
            </div>
          </div>
        </div>
      </a-spin>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Button, Spin, message } from 'ant-design-vue'
import { getChatRoomList, ChatRoom } from '../api/chat'

const AButton = Button
const ASpin = Spin

const router = useRouter()
const rooms = ref<ChatRoom[]>([])
const loading = ref(false)

const loadRooms = async () => {
  loading.value = true
  try {
    rooms.value = await getChatRoomList()
  } catch (error) {
    message.error('加载历史记录失败')
    console.error('Error loading rooms:', error)
  } finally {
    loading.value = false
  }
}

const viewRoom = (roomId: number) => {
  router.push(`/chat/${roomId}`)
}

const goBack = () => {
  router.push('/')
}

const formatDate = (timestamp: number) => {
  const date = new Date(timestamp)
  return date.toLocaleString('zh-CN')
}

onMounted(() => {
  loadRooms()
})
</script>

<style scoped>
.room-history {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 24px;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  background: white;
  border-radius: 16px;
  padding: 32px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
  min-height: 80vh;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  padding-bottom: 16px;
  border-bottom: 2px solid #f0f0f0;
}

.header h2 {
  margin: 0;
  font-size: 28px;
  font-weight: 600;
  color: #1a1a1a;
}

.empty-state {
  text-align: center;
  padding: 80px 24px;
}

.empty-icon {
  font-size: 80px;
  margin-bottom: 16px;
}

.empty-state p {
  font-size: 18px;
  color: #999;
  margin-bottom: 24px;
}

.rooms-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
  margin-top: 24px;
}

.room-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  padding: 24px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  color: white;
}

.room-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
}

.room-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.3);
}

.room-icon {
  font-size: 32px;
}

.room-id {
  font-size: 20px;
  font-weight: 600;
}

.room-info {
  font-size: 14px;
  opacity: 0.9;
}

.room-info p {
  margin: 8px 0;
}

@media (max-width: 768px) {
  .room-history {
    padding: 16px;
  }

  .container {
    padding: 24px 16px;
  }

  .header {
    flex-direction: column;
    gap: 16px;
    align-items: flex-start;
  }

  .header h2 {
    font-size: 24px;
  }

  .rooms-grid {
    grid-template-columns: 1fr;
  }
}
</style>
