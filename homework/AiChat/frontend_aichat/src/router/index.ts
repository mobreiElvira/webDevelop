// router/index.ts
import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../views/HomePage.vue'
import ChatRoom from '../views/ChatRoom.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePage
    },
    // 无参路径：/chat（默认进入ChatRoom，无历史对话）
    {
      path: '/chat',
      name: 'chat',
      component: ChatRoom
    },
    // 带参路径：/chat/:roomId（加载对应历史对话）
    {
      path: '/chat/:roomId',
      name: 'chatWithId',
      component: ChatRoom
    }
  ]
})

export default router