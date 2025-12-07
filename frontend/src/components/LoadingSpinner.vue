<template>
  <div class="loading-overlay" v-if="isLoading">
    <div class="loading-container">
      <div class="spinner"></div>
      <h3>Analyzing Symptoms</h3>
      <p>Our expert system is diagnosing your car...</p>
      <div class="loading-progress">
        <div class="progress-bar" :style="{ width: `${progress}%` }"></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'

const props = defineProps({
  isLoading: {
    type: Boolean,
    required: true
  }
})

const progress = ref(0)

const startProgress = () => {
  progress.value = 0
  const interval = setInterval(() => {
    progress.value += 5
    if (progress.value >= 95) {
      clearInterval(interval)
    }
  }, 200)
}

watch(() => props.isLoading, (newVal) => {
  if (newVal) {
    startProgress()
  } else {
    progress.value = 100
    setTimeout(() => {
      progress.value = 0
    }, 500)
  }
})
</script>

<style scoped>
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
  animation: fadeIn 0.3s ease;
}

.loading-container {
  text-align: center;
  max-width: 400px;
  padding: 2rem;
  background: white;
  border-radius: var(--border-radius);
  box-shadow: var(--shadow);
}

.spinner {
  width: 80px;
  height: 80px;
  border: 6px solid #f3f3f3;
  border-top: 6px solid var(--primary-color);
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin: 0 auto 2rem;
}

h3 {
  color: var(--dark-color);
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
}

p {
  color: var(--gray-color);
  margin-bottom: 1.5rem;
}

.loading-progress {
  width: 100%;
  height: 6px;
  background: #f3f3f3;
  border-radius: 3px;
  overflow: hidden;
}

.progress-bar {
  height: 100%;
  background: linear-gradient(90deg, var(--primary-color), var(--secondary-color));
  border-radius: 3px;
  transition: width 0.3s ease;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}
</style>