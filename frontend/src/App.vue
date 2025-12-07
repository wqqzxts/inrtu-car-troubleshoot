<template>
  <div class="app-container">
    <div class="app-header">
      <div class="header-content">
        <h1>Car Troubleshooting Expert System</h1>
        <p class="header-subtitle">
          Diagnosis based on expert system rules
        </p>
      </div>      
    </div>

    <div class="app-main">
      <div class="sidebar">
        <div class="sidebar-section">
          <h3>Recent Diagnoses</h3>
          <div 
            v-for="record in store.symptomsHistory" 
            :key="record.timestamp"
            class="history-item"
            @click="selectHistory(record)"
          >
            <div class="history-time">
              {{ formatHistoryTime(record.timestamp) }}
            </div>
            <div class="history-diagnosis">
              {{ record.diagnosis }}
            </div>
          </div>
          <div v-if="store.symptomsHistory.length === 0" class="empty-history">
            No diagnosis history yet
          </div>
        </div>

        <div class="sidebar-section">
          <h3>Quick Tips</h3>
          <div class="tips">
            <div class="tip">
              <div class="tip-icon">💡</div>
              <div class="tip-content">
                <strong>Be Specific:</strong> The more detailed your symptoms, the more accurate the diagnosis.
              </div>
            </div>
            <div class="tip">
              <div class="tip-icon">⚡</div>
              <div class="tip-content">
                <strong>Recent Issues:</strong> Note any recent repairs or maintenance.
              </div>
            </div>
            <div class="tip">
              <div class="tip-icon">🔍</div>
              <div class="tip-content">
                <strong>Check Basics:</strong> Always verify fluid levels and tire pressure first.
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="content">
        <SymptomForm />
        
        <div v-if="store.error" class="error-alert">
          <div class="error-icon">⚠️</div>
          <div class="error-content">
            <strong>Error:</strong> {{ store.error }}
          </div>
          <button @click="store.error = null" class="error-close">×</button>
        </div>

        <DiagnosisResult v-if="store.diagnosis" :diagnosis="store.diagnosis" />
        <RuleTrace v-if="store.diagnosis" :diagnosis="store.diagnosis" />
      </div>
    </div>

    <LoadingSpinner :isLoading="store.isLoading" />
  </div>
</template>

<script setup>
import { useTroubleshooterStore } from '@/store/troubleshooter'
import SymptomForm from '@/components/SymptomForm.vue'
import DiagnosisResult from '@/components/DiagnosisResult.vue'
import RuleTrace from '@/components/RuleTrace.vue'
import LoadingSpinner from '@/components/LoadingSpinner.vue'

const store = useTroubleshooterStore()

const formatHistoryTime = (timestamp) => {
  const date = new Date(timestamp)
  const now = new Date()
  const diff = now - date
  const minutes = Math.floor(diff / 60000)
  const hours = Math.floor(diff / 3600000)
  const days = Math.floor(diff / 86400000)
  
  if (minutes < 60) return `${minutes} min ago`
  if (hours < 24) return `${hours} hours ago`
  return `${days} days ago`
}

const selectHistory = (record) => {
  // Could implement history selection to repopulate form
  console.log('Selected history:', record)
}
</script>

<style scoped>
.app-header {
  background: linear-gradient(135deg, #1e293b 0%, #0f172a 100%);
  color: white;
  padding: 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: relative;
  overflow: hidden;
}

.app-header::before {
  content: '';
  position: absolute;
  top: 0;
  right: 0;
  width: 200px;
  height: 200px;
  background: radial-gradient(circle, rgba(59, 130, 246, 0.2) 0%, transparent 70%);
}

.header-content h1 {
  font-size: 2.5rem;
  font-weight: 800;
  margin-bottom: 0.5rem;
  background: linear-gradient(135deg, #60a5fa 0%, #93c5fd 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.header-subtitle {
  color: #94a3b8;
  font-size: 1.1rem;
  max-width: 600px;
}

.header-badge {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  padding: 0.75rem 1.5rem;
  border-radius: 50px;
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.badge-text {
  background: linear-gradient(135deg, #10b981 0%, #34d399 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  font-weight: 700;
  font-size: 1.1rem;
  letter-spacing: 1px;
}

.app-main {
  display: flex;
  min-height: calc(100vh - 160px);
}

.sidebar {
  width: 300px;
  background: #f8fafc;
  border-right: 1px solid #e2e8f0;
  padding: 2rem;
  overflow-y: auto;
}

.content {
  flex: 1;
  padding: 2rem;
  overflow-y: auto;
}

.sidebar-section {
  margin-bottom: 2rem;
}

.sidebar-section h3 {
  color: var(--dark-color);
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 1rem;
  padding-bottom: 0.5rem;
  border-bottom: 2px solid #e2e8f0;
}

.history-item {
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  padding: 1rem;
  margin-bottom: 0.75rem;
  cursor: pointer;
  transition: var(--transition);
}

.history-item:hover {
  border-color: var(--primary-color);
  transform: translateX(5px);
  box-shadow: 0 4px 6px rgba(59, 130, 246, 0.1);
}

.history-time {
  color: var(--gray-color);
  font-size: 0.875rem;
  font-weight: 500;
  margin-bottom: 0.25rem;
}

.history-diagnosis {
  color: var(--dark-color);
  font-weight: 500;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.empty-history {
  text-align: center;
  color: var(--gray-color);
  font-style: italic;
  padding: 1rem;
  background: #f1f5f9;
  border-radius: 8px;
  border: 1px dashed #cbd5e0;
}

.tips {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.tip {
  display: flex;
  gap: 1rem;
  background: white;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  padding: 1rem;
  transition: var(--transition);
}

.tip:hover {
  border-color: var(--primary-color);
  box-shadow: 0 4px 6px rgba(59, 130, 246, 0.1);
}

.tip-icon {
  font-size: 1.5rem;
}

.tip-content {
  flex: 1;
}

.tip-content strong {
  color: var(--dark-color);
  display: block;
  margin-bottom: 0.25rem;
}

.tip-content {
  color: var(--gray-color);
  font-size: 0.9rem;
  line-height: 1.4;
}

.error-alert {
  background: linear-gradient(135deg, #fee, #fdd);
  border: 1px solid #fecaca;
  border-radius: var(--border-radius);
  padding: 1rem 1.5rem;
  display: flex;
  align-items: center;
  gap: 1rem;
  margin: 1rem 0;
  animation: slideIn 0.3s ease;
}

.error-icon {
  font-size: 1.5rem;
}

.error-content {
  flex: 1;
  color: #dc2626;
}

.error-close {
  background: none;
  border: none;
  color: #dc2626;
  font-size: 1.5rem;
  cursor: pointer;
  padding: 0;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 4px;
  transition: var(--transition);
}

.error-close:hover {
  background: #fecaca;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@media (max-width: 1024px) {
  .app-main {
    flex-direction: column;
  }
  
  .sidebar {
    width: 100%;
    border-right: none;
    border-bottom: 1px solid #e2e8f0;
  }
}

@media (max-width: 768px) {
  .app-header {
    flex-direction: column;
    text-align: center;
    gap: 1rem;
  }
  
  .header-content h1 {
    font-size: 2rem;
  }
  
  .sidebar,
  .content {
    padding: 1rem;
  }
}
</style>