<template>
  <div class="rule-trace" v-if="diagnosis && diagnosis.ruleChain.length > 0">
    <div class="trace-header">
      <h3>Expert System Rule Trace</h3>
      <p class="subtitle">See how the expert system reached its diagnosis</p>
    </div>

    <div class="timeline">
      <div 
        v-for="(rule, index) in diagnosis.ruleChain" 
        :key="rule.ruleId"
        class="timeline-item"
        :class="{ 'active': isActive(index) }"
      >
        <div class="timeline-marker">
          <div class="marker"></div>
        </div>
        
        <div class="timeline-content">
          <div class="rule-header">
            <span class="rule-id">Rule #{{ index + 1 }}</span>
            <span class="rule-name">{{ rule.ruleName }}</span>            
          </div>
          
          <div class="rule-details">
            <div class="detail-group">
              <strong>Condition:</strong>
              <code class="condition">{{ rule.condition }}</code>
            </div>
            
            <div class="detail-group">
              <strong>Action:</strong>
              <span class="action">{{ rule.action }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'

const props = defineProps({
  diagnosis: {
    type: Object,
    default: null
  }
})

const activeIndex = ref(null)

const isActive = (index) => {
  return activeIndex.value === index
}

</script>

<style scoped>
.rule-trace {
  background: white;
  border-radius: var(--border-radius);
  box-shadow: var(--shadow);
  margin: 2rem 0;
  overflow: hidden;
}

.trace-header {
  background: linear-gradient(135deg, #1e293b 0%, #334155 100%);
  color: white;
  padding: 1.5rem;
}

.trace-header h3 {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
}

.subtitle {
  color: rgba(255, 255, 255, 0.8);
  font-size: 0.95rem;
}

.timeline {
  padding: 2rem;
  position: relative;
}

.timeline::before {
  content: '';
  position: absolute;
  left: 60px;
  top: 0;
  bottom: 0;
  width: 2px;
  background: linear-gradient(to bottom, #3b82f6, #10b981);
}

.timeline-item {
  display: flex;
  margin-bottom: 2rem;
  position: relative;
  opacity: 0.7;
  transition: all 0.3s ease;
}

.timeline-item.active {
  opacity: 1;
}

.timeline-item:last-child {
  margin-bottom: 0;
}

.timeline-marker {
  width: 80px;
  display: flex;
  justify-content: center;
  position: relative;
  z-index: 1;
}

.marker {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background: white;
  border: 4px solid #3b82f6;
  box-shadow: 0 0 0 4px rgba(59, 130, 246, 0.2);
  transition: all 0.3s ease;
}

.timeline-item.active .marker {
  background: #3b82f6;
  transform: scale(1.2);
  box-shadow: 0 0 0 6px rgba(59, 130, 246, 0.3);
}

.timeline-content {
  flex: 1;
  background: #f8fafc;
  border-radius: var(--border-radius);
  padding: 1.5rem;
  border: 1px solid #e2e8f0;
  transition: all 0.3s ease;
  transform: translateX(-20px);
}

.timeline-item.active .timeline-content {
  background: white;
  border-color: #3b82f6;
  box-shadow: 0 4px 6px rgba(59, 130, 246, 0.1);
  transform: translateX(0);
}

.rule-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
  padding-bottom: 0.75rem;
  border-bottom: 2px solid #e2e8f0;
}

.rule-id {
  background: var(--primary-color);
  color: white;
  padding: 0.25rem 0.75rem;
  border-radius: 4px;
  font-size: 0.75rem;
  font-weight: 600;
}

.rule-name {
  flex-grow: 1;
  margin: 0 1rem;
  font-weight: 600;
  color: var(--dark-color);
}

.rule-time {
  color: var(--gray-color);
  font-size: 0.875rem;
  font-family: 'Monaco', 'Courier New', monospace;
}

.rule-details {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.detail-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.detail-group strong {
  color: var(--dark-color);
  font-size: 0.9rem;
}

.condition {
  background: #1e293b;
  color: #94a3b8;
  padding: 1rem;
  border-radius: 6px;
  font-family: 'Monaco', 'Courier New', monospace;
  font-size: 0.9rem;
  overflow-x: auto;
  border-left: 3px solid #3b82f6;
}

.action {
  background: #f0f9ff;
  color: var(--primary-color);
  padding: 1rem;
  border-radius: 6px;
  font-family: 'Monaco', 'Courier New', monospace;
  font-size: 0.9rem;
  border-left: 3px solid #10b981;
}

@media (max-width: 768px) {
  .timeline-content {
    padding: 1rem;
  }
  
  .rule-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.5rem;
  }
  
  .rule-time {
    align-self: flex-end;
  }
}
</style>