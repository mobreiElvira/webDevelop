<template>
  <div class="home-page">
    <!-- 背景装饰元素 -->
    <div class="bg-decoration bg-circle circle-1"></div>
    <div class="bg-decoration bg-circle circle-2"></div>
    <div class="bg-decoration bg-circle circle-3"></div>
    <div class="bg-decoration bg-grid"></div>

    <div class="container">
      <!-- 标题区域（带渐变文字和动画） -->
      <div class="title-container">
        <h1 class="title">
          <span class="gradient-text">AI 脑筋急转弯</span>
        </h1>
        <p class="subtitle">挑战智商极限，解锁趣味问答</p>
      </div>

      <!-- 按钮区域（带悬浮动画和层次感） -->
      <div class="button-group">
        <a-button
          type="primary"
          size="large"
          class="start-button"
          @click="startGame"
        >
          <span class="button-text">开始游戏</span>
          <span class="button-icon">→</span>
        </a-button>
      </div>

      <!-- 底部装饰文本 -->
      <div class="footer-text">
        <p>💡 已有 {{ Math.floor(Math.random() * 1000) + 500 }}+ 用户参与挑战</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { Button } from 'ant-design-vue'

const AButton = Button
const router = useRouter()

// 开始游戏（新建房间）
const startGame = () => {
  const roomId = Date.now()
  router.push(`/chat/${roomId}`)
}

// 前往历史对话列表
const goToHistory = () => {
  router.push('/chat')
}
</script>
<style scoped>
/* 基础布局 - 优化渐变和溢出处理 */
.home-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #4361ee 0%, #3a0ca3 100%);
  position: relative;
  overflow: hidden;
  padding: 20px;
  font-family: 'Inter', 'PingFang SC', 'Microsoft YaHei', sans-serif;
}

/* 背景装饰元素 - 增强层次感和动态感 */
.bg-decoration {
  position: absolute;
  pointer-events: none;
  opacity: 0.12;
  transition: opacity 1s ease;
}

/* 滚动时背景装饰轻微淡化 */
.home-page:hover .bg-decoration {
  opacity: 0.18;
}

.bg-circle {
  border-radius: 50%;
  background: white;
  animation: float 10s ease-in-out infinite;
  filter: blur(2px);
}

.circle-1 {
  width: 380px;
  height: 380px;
  top: -10%;
  left: -10%;
  animation-delay: 0s;
}

.circle-2 {
  width: 280px;
  height: 280px;
  bottom: -15%;
  right: -10%;
  animation-delay: 3s;
}

.circle-3 {
  width: 220px;
  height: 220px;
  top: 60%;
  left: 70%;
  animation-delay: 6s;
}

/* 网格背景优化 - 更细腻的线条 */
.bg-grid {
  width: 100%;
  height: 100%;
  background-image: 
    linear-gradient(to right, rgba(255,255,255,0.08) 1px, transparent 1px),
    linear-gradient(to bottom, rgba(255,255,255,0.08) 1px, transparent 1px);
  background-size: 40px 40px;
  animation: gridMove 20s linear infinite;
}

/* 容器样式 - 增强玻璃拟态和光影 */
.container {
  text-align: center;
  padding: 70px 45px;
  background: rgba(255, 255, 255, 0.97);
  border-radius: 32px;
  box-shadow: 0 25px 100px rgba(0, 0, 0, 0.3);
  min-width: 350px;
  max-width: 520px;
  width: 100%;
  position: relative;
  z-index: 1;
  backdrop-filter: blur(16px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  transition: all 0.5s cubic-bezier(0.16, 1, 0.3, 1);
}

/* 容器悬浮效果 - 轻微上浮和阴影增强 */
.container:hover {
  transform: translateY(-5px);
  box-shadow: 0 30px 120px rgba(0, 0, 0, 0.35);
  border-color: rgba(255, 255, 255, 0.5);
}

/* 标题容器 - 增加顶部装饰线 */
.title-container {
  margin-bottom: 70px;
  position: relative;
}

.title-container::before {
  content: '';
  position: absolute;
  top: -20px;
  left: 50%;
  transform: translateX(-50%);
  width: 60px;
  height: 4px;
  background: linear-gradient(90deg, #4361ee, #3a0ca3);
  border-radius: 2px;
  animation: lineGrow 1.2s ease-out;
}

/* 标题样式 - 优化字体和渐变 */
.title {
  font-size: 58px;
  font-weight: 800;
  margin-bottom: 20px;
  color: #1a1a1a;
  letter-spacing: 4px;
  position: relative;
  display: inline-block;
  text-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

/* 渐变文字增强 - 更饱满的色彩过渡 */
.gradient-text {
  background: linear-gradient(135deg, #4361ee 0%, #7209b7 30%, #f72585 100%);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  display: inline-block;
  animation: fadeInUp 1s ease-out, textGlow 3s ease-in-out infinite alternate;
  position: relative;
}

/* 标题文字发光效果 */
@keyframes textGlow {
  from {
    text-shadow: 0 0 10px rgba(67, 97, 238, 0.2), 0 0 20px rgba(67, 97, 238, 0.1);
  }
  to {
    text-shadow: 0 0 15px rgba(67, 97, 238, 0.3), 0 0 30px rgba(67, 97, 238, 0.2);
  }
}

/* 副标题优化 - 更柔和的颜色和字体 */
.subtitle {
  font-size: 20px;
  color: #555;
  margin: 0;
  font-weight: 500;
  letter-spacing: 1.5px;
  animation: fadeInUp 1s ease-out 0.4s both;
  opacity: 0;
  line-height: 1.6;
}

/* 按钮组 - 优化间距和布局 */
.button-group {
  display: flex;
  flex-direction: column;
  gap: 24px;
  margin-bottom: 60px;
  padding: 0 20px;
}

/* 开始按钮 - 增强渐变和动态效果 */
.start-button {
  width: 100%;
  height: 72px;
  font-size: 24px;
  border-radius: 36px;
  font-weight: 700;
  letter-spacing: 3px;
  background: linear-gradient(135deg, #4361ee 0%, #3a0ca3 100%);
  border: none;
  transition: all 0.4s cubic-bezier(0.16, 1, 0.3, 1);
  overflow: hidden;
  position: relative;
  box-shadow: 0 15px 40px rgba(58, 12, 163, 0.45);
  color: white !important;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 按钮渐变叠加层 -  hover时增强 */
.start-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, rgba(255,255,255,0.1) 0%, rgba(255,255,255,0) 100%);
  transform: translateX(-100%);
  transition: transform 0.6s ease;
}

.start-button:hover::before {
  transform: translateX(100%);
}

.start-button:hover {
  transform: translateY(-6px);
  box-shadow: 0 20px 50px rgba(58, 12, 163, 0.55);
  background: linear-gradient(135deg, #3a56d4 0%, #310887 100%);
}

.start-button:active {
  transform: translateY(-2px);
  box-shadow: 0 10px 30px rgba(58, 12, 163, 0.45);
}

/* 历史按钮 - 优化边框和阴影 */
.history-button {
  width: 100%;
  height: 62px;
  font-size: 20px;
  border-radius: 32px;
  font-weight: 600;
  letter-spacing: 1.5px;
  background: rgba(255, 255, 255, 0.9);
  border: 1.5px solid #e0e0e0;
  color: #333 !important;
  transition: all 0.4s cubic-bezier(0.16, 1, 0.3, 1);
  overflow: hidden;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 历史按钮 hover 渐变边框 */
.history-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  border-radius: 32px;
  padding: 1.5px;
  background: linear-gradient(135deg, #4361ee, #3a0ca3);
  -webkit-mask: 
    linear-gradient(#fff 0 0) content-box, 
    linear-gradient(#fff 0 0);
  mask: 
    linear-gradient(#fff 0 0) content-box, 
    linear-gradient(#fff 0 0);
  -webkit-mask-composite: xor;
  mask-composite: exclude;
  pointer-events: none;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.history-button:hover::before {
  opacity: 1;
}

.history-button:hover {
  transform: translateY(-6px);
  background: white;
  border-color: transparent;
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.08);
  color: #3a0ca3 !important;
}

.history-button:active {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.06);
}

/* 按钮文本和图标 - 优化间距和动画 */
.button-text {
  margin-right: 12px;
  position: relative;
}

/* 按钮文本下划线动画 */
.start-button:hover .button-text::after,
.history-button:hover .button-text::after {
  width: 100%;
}

.button-text::after {
  content: '';
  position: absolute;
  bottom: -4px;
  left: 0;
  width: 0;
  height: 2px;
  background: currentColor;
  transition: width 0.3s ease;
  border-radius: 1px;
}

.button-icon {
  transition: transform 0.4s cubic-bezier(0.16, 1, 0.3, 1);
  font-size: 22px;
}

.start-button:hover .button-icon,
.history-button:hover .button-icon {
  transform: translateX(8px) scale(1.1);
}

/* 底部文本 - 优化样式和动画 */
.footer-text {
  font-size: 15px;
  color: #888;
  animation: fadeIn 1.8s ease-out 0.8s both;
  opacity: 0;
  position: relative;
  padding-top: 15px;
}

.footer-text::before {
  content: '';
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 80px;
  height: 1px;
  background: #eee;
}

.footer-text p {
  margin: 0;
  letter-spacing: 0.8px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

/* 动态数字跳动效果 */
.footer-text .count {
  color: #4361ee;
  font-weight: 600;
  animation: countUp 2s ease-out 1s both;
  opacity: 0;
  position: relative;
}

/* 图标旋转动画 */
.footer-text .icon {
  animation: rotate 8s linear infinite;
}

/* 动画效果优化 - 更流畅的曲线 */
@keyframes float {
  0% {
    transform: translate(0, 0) scale(1);
  }
  50% {
    transform: translate(25px, -20px) scale(1.08);
  }
  100% {
    transform: translate(0, 0) scale(1);
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
    filter: blur(4px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
    filter: blur(0);
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
    filter: blur(2px);
  }
  to {
    opacity: 1;
    filter: blur(0);
  }
}

@keyframes lineGrow {
  from {
    width: 0;
    opacity: 0;
  }
  to {
    width: 60px;
    opacity: 1;
  }
}

@keyframes gridMove {
  from {
    background-position: 0 0;
  }
  to {
    background-position: 40px 40px;
  }
}

@keyframes countUp {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes rotate {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

/* 响应式调整 - 更精细的适配 */
@media (max-width: 768px) {
  .container {
    padding: 55px 30px;
    box-shadow: 0 20px 80px rgba(0, 0, 0, 0.25);
    border-radius: 28px;
  }

  .container:hover {
    transform: translateY(-3px);
    box-shadow: 0 25px 90px rgba(0, 0, 0, 0.3);
  }

  .title {
    font-size: 46px;
    letter-spacing: 3px;
    margin-bottom: 16px;
  }

  .subtitle {
    font-size: 18px;
    letter-spacing: 1px;
  }

  .button-group {
    gap: 20px;
    margin-bottom: 50px;
    padding: 0 15px;
  }

  .start-button {
    height: 64px;
    font-size: 22px;
    letter-spacing: 2px;
  }

  .history-button {
    height: 56px;
    font-size: 18px;
  }

  /* 调整装饰元素大小 */
  .circle-1 {
    width: 280px;
    height: 280px;
  }

  .circle-2 {
    width: 220px;
    height: 220px;
  }

  .circle-3 {
    width: 180px;
    height: 180px;
  }

  .title-container {
    margin-bottom: 60px;
  }

  .title-container::before {
    width: 50px;
  }
}

@media (max-width: 480px) {
  .container {
    padding: 45px 20px;
    min-width: auto;
    border-radius: 24px;
  }

  .title {
    font-size: 38px;
    letter-spacing: 2px;
  }

  .subtitle {
    font-size: 16px;
  }

  .start-button {
    height: 58px;
    font-size: 20px;
  }

  .history-button {
    height: 52px;
    font-size: 17px;
  }

  .button-icon {
    font-size: 20px;
  }

  .footer-text {
    font-size: 14px;
  }

  .title-container {
    margin-bottom: 50px;
  }
}

/* 深色模式适配（可选） */
@media (prefers-color-scheme: dark) {
  .home-page {
    background: linear-gradient(135deg, #1a1a2e 0%, #16213e 100%);
  }

  .container {
    background: rgba(25, 25, 45, 0.95);
    border-color: rgba(255, 255, 255, 0.1);
  }

  .title {
    color: #f8f8f8;
    text-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  }

  .subtitle {
    color: #bbb;
  }

  .history-button {
    background: rgba(40, 40, 60, 0.8);
    border-color: #333;
    color: #eee !important;
  }

  .history-button:hover {
    background: rgba(45, 45, 65, 1);
    color: #a78bfa !important;
  }

  .footer-text {
    color: #777;
  }

  .footer-text::before {
    background: #333;
  }

  .bg-circle {
    background: rgba(255, 255, 255, 0.8);
  }
}
</style>