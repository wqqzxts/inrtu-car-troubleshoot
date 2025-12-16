<template>
  <div class="diagnosis-result" v-if="diagnosis">
    <div class="result-header">
      <h2>Diagnosis Result</h2>
      <div class="confidence-badge" :class="confidenceClass">
        {{ Math.round(diagnosis.confidence) }}% Confidence
      </div>
    </div>

    <div class="problem-card">
      <div class="problem-header">
        <div class="severity-badge" :class="severityClass">
          {{ diagnosis.problem.severity }}
        </div>
        <h3 class="problem-name">{{ diagnosis.problem.name }}</h3>
        <div class="category-tag">
          {{ diagnosis.problem.category.replace('_', ' ') }}
        </div>
      </div>

      <div class="problem-content">
        <p class="problem-description">{{ diagnosis.problem.description }}</p>

        <div class="problem-details">
          <div class="detail-section">
            <h4>Possible Causes</h4>
            <ul class="detail-list">
              <li v-for="cause in diagnosis.problem.possibleCauses" :key="cause">
                {{ cause }}
              </li>
            </ul>
          </div>

          <div class="detail-section">
            <h4>Recommended Solutions</h4>
            <ul class="detail-list">
              <li v-for="solution in diagnosis.problem.solutions" :key="solution">
                {{ solution }}
              </li>
            </ul>
          </div>

          <div class="detail-section" v-if="diagnosis.problem.estimatedRepairCost">
            <h4>Repair Information</h4>
            <div class="repair-info">
              <div class="info-item">
                <span class="info-label">Estimated Cost:</span>
                <span class="info-value">${{ diagnosis.problem.estimatedRepairCost.toFixed(2) }}</span>
              </div>
              <div class="info-item" v-if="diagnosis.problem.estimatedRepairTime">
                <span class="info-label">Estimated Time:</span>
                <span class="info-value">{{ diagnosis.problem.estimatedRepairTime }}</span>
              </div>
            </div>
          </div>
        </div>

        <div class="matched-symptoms">
          <h4>Matched Symptoms</h4>
          <div class="symptoms-tags">
            <span 
              v-for="symptom in diagnosis.matchedSymptoms" 
              :key="symptom"
              class="symptom-tag"
            >
              {{ symptom }}
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  diagnosis: {
    type: Object,
    required: true
  }
})

const confidenceClass = computed(() => {
  const confidence = props.diagnosis.confidence
  if (confidence >= 0.8) return 'confidence-high'
  if (confidence >= 0.6) return 'confidence-medium'
  return 'confidence-low'
})

const severityClass = computed(() => {
  const severity = props.diagnosis.problem.severity.toLowerCase()
  return `severity-${severity}`
})
</script>

<style scoped>
.diagnosis-result {
  background: white;
  border-radius: var(--border-radius);
  box-shadow: var(--shadow);
  margin: 2rem 0;
  overflow: hidden;
}

.result-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 1.5rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.result-header h2 {
  font-size: 1.5rem;
  font-weight: 600;
}

.confidence-badge {
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-size: 0.875rem;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.confidence-high {
  background: rgba(16, 185, 129, 0.9);
}

.confidence-medium {
  background: rgba(245, 158, 11, 0.9);
}

.confidence-low {
  background: rgba(239, 68, 68, 0.9);
}

.problem-card {
  padding: 2rem;
}

.problem-header {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1.5rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #e2e8f0;
}

.severity-badge {
  padding: 0.375rem 0.75rem;
  border-radius: 6px;
  font-size: 0.75rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  color: white;
}

.severity-critical {
  background: linear-gradient(135deg, #ef4444, #dc2626);
}

.severity-high {
  background: linear-gradient(135deg, #f97316, #ea580c);
}

.severity-medium {
  background: linear-gradient(135deg, #f59e0b, #d97706);
}

.severity-low {
  background: linear-gradient(135deg, #10b981, #059669);
}

.problem-name {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--dark-color);
  flex-grow: 1;
}

.category-tag {
  padding: 0.375rem 0.75rem;
  background: #e0f2fe;
  color: var(--primary-color);
  border-radius: 6px;
  font-size: 0.875rem;
  font-weight: 500;
}

.problem-content {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.problem-description {
  font-size: 1.1rem;
  line-height: 1.6;
  color: var(--dark-color);
}

.problem-details {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  margin-top: 1rem;
}

.detail-section {
  background: #f8fafc;
  border-radius: var(--border-radius);
  padding: 1.5rem;
  border: 1px solid #e2e8f0;
}

.detail-section h4 {
  color: var(--dark-color);
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 1rem;
  padding-bottom: 0.5rem;
  border-bottom: 2px solid var(--primary-color);
}

.detail-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.detail-list li {
  padding: 0.5rem 0;
  color: var(--gray-color);
  position: relative;
  padding-left: 1.5rem;
}

.detail-list li::before {
  content: '•';
  color: var(--primary-color);
  position: absolute;
  left: 0;
  font-size: 1.5rem;
  line-height: 1;
}

.repair-info {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.5rem 0;
  border-bottom: 1px dashed #e2e8f0;
}

.info-label {
  color: var(--gray-color);
  font-weight: 500;
}

.info-value {
  color: var(--dark-color);
  font-weight: 600;
  font-size: 1.1rem;
}

.matched-symptoms {
  background: #f0f9ff;
  border-radius: var(--border-radius);
  padding: 1.5rem;
  border: 1px solid #bae6fd;
}

.matched-symptoms h4 {
  color: var(--primary-color);
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 1rem;
}

.symptoms-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.75rem;
}

.symptom-tag {
  background: white;
  color: var(--primary-color);
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-size: 0.875rem;
  font-weight: 500;
  border: 1px solid var(--primary-color);
  transition: var(--transition);
}

.symptom-tag:hover {
  background: var(--primary-color);
  color: white;
  transform: translateY(-2px);
}

.timestamp {
  text-align: center;
  color: var(--gray-color);
  font-size: 0.875rem;
  padding-top: 1rem;
  border-top: 1px solid #e2e8f0;
}
</style>