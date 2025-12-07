import axios from 'axios'

const API_BASE_URL = 'http://localhost:8000'

const expertSystemApi = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 30000,
})

export const troubleshootCar = async (symptoms) => {
  try {
    const response = await expertSystemApi.post('/api/troubleshoot', symptoms)
    return response.data
  } catch (error) {
    if (error.response) {
      throw new Error(error.response.data.error || 'Server error occurred')
    } else if (error.request) {
      throw new Error('No response from server. Please check your connection.')
    } else {
      throw new Error('Request setup failed: ' + error.message)
    }
  }
}

export default expertSystemApi