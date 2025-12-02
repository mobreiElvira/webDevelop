import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

const apiClient = axios.create({
  baseURL: BASE_URL,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json'
  }
})

export interface ChatRoom {
  id: number
  [key: string]: any
}

export const sendMessage = async (roomId: number, userPrompt: string): Promise<string> => {
  const response = await apiClient.post(`/${roomId}/chat`, null, {
    params: {
      userPrompt
    }
  })
  return response.data
}

export const getChatRoomList = async (): Promise<ChatRoom[]> => {
  const response = await apiClient.get('/rooms')
  return response.data
}
