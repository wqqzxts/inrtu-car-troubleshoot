<template>
  <div class="symptom-form">
    <div class="form-header">
      <h2>Car Troubleshooting Expert System</h2>
      <p class="subtitle">Describe your car's symptoms for diagnosis</p>
    </div>

    <form @submit.prevent="handleSubmit" class="form-grid">
      <!-- Basic Information Section -->
      <div class="form-section">
        <h3 class="section-title">
          <span class="icon">🚗</span>
          Basic Information
        </h3>
        <div class="grid-2">
          <div class="form-group">
            <label for="engineType" class="form-label">
              Engine Type <span class="required">*</span>
            </label>
            <select 
              id="engineType" 
              v-model="store.formData.engineType"
              class="form-select"
              required
            >
              <option value="GASOLINE">Gasoline</option>
              <option value="DIESEL">Diesel</option>
              <option value="HYBRID">Hybrid</option>
              <option value="ELECTRIC">Electric</option>
              <option value="LPG">LPG</option>
            </select>
          </div>

          <div class="form-group">
            <label for="mileage" class="form-label">
              Mileage (km) <span class="required">*</span>
            </label>
            <input 
              type="number" 
              id="mileage" 
              v-model.number="store.formData.mileage"
              min="0"
              max="500000"
              class="form-input"
              required
            >
          </div>

          <div class="form-group">
            <label for="lastServiceMonths" class="form-label">
              Months Since Last Service
            </label>
            <input 
              type="number" 
              id="lastServiceMonths" 
              v-model.number="store.formData.lastServiceMonths"
              min="0"
              max="120"
              class="form-input"
            >
          </div>
        </div>
      </div>

      <!-- Engine Symptoms Section -->
      <div class="form-section">
        <h3 class="section-title">
          <span class="icon">⚙️</span>
          Engine Symptoms
        </h3>
        <div class="grid-2">
          <div class="form-group">
            <label for="engineNoise" class="form-label">Engine Noise</label>
            <select 
              id="engineNoise" 
              v-model="store.formData.engineNoise"
              class="form-select"
            >
              <option value="">None</option>
              <option value="knocking">Knocking</option>
              <option value="tapping">Tapping</option>
              <option value="hissing">Hissing</option>
              <option value="whistling">Whistling</option>
              <option value="grinding">Grinding</option>
            </select>
          </div>

          <div class="form-group">
            <label for="smokeFromExhaust" class="form-label">Exhaust Smoke</label>
            <select 
              id="smokeFromExhaust" 
              v-model="store.formData.smokeFromExhaust"
              class="form-select"
            >
              <option value="">None</option>
              <option value="white">White Smoke</option>
              <option value="blue">Blue Smoke</option>
              <option value="black">Black Smoke</option>
            </select>
          </div>
        </div>

        <div class="checkbox-grid">
          <label class="checkbox-label">
            <input 
              type="checkbox" 
              v-model="store.formData.startingIssue"
              class="checkbox-input"
            >
            <span class="checkbox-custom"></span>
            <span>Starting Issues</span>
          </label>

          <label class="checkbox-label">
            <input 
              type="checkbox" 
              v-model="store.formData.stalling"
              class="checkbox-input"
            >
            <span class="checkbox-custom"></span>
            <span>Stalling</span>
          </label>

          <label class="checkbox-label">
            <input 
              type="checkbox" 
              v-model="store.formData.overheating"
              class="checkbox-input"
            >
            <span class="checkbox-custom"></span>
            <span>Overheating</span>
          </label>

          <label class="checkbox-label">
            <input 
              type="checkbox" 
              v-model="store.formData.fuelConsumptionIncreased"
              class="checkbox-input"
            >
            <span class="checkbox-custom"></span>
            <span>Increased Fuel Consumption</span>
          </label>

          <label class="checkbox-label">
            <input 
              type="checkbox" 
              v-model="store.formData.lossOfPower"
              class="checkbox-input"
            >
            <span class="checkbox-custom"></span>
            <span>Loss of Power</span>
          </label>

          <label class="checkbox-label">
            <input 
              type="checkbox" 
              v-model="store.formData.roughIdling"
              class="checkbox-input"
            >
            <span class="checkbox-custom"></span>
            <span>Rough Idling</span>
          </label>
        </div>
      </div>

      <!-- System Issues Section -->
      <div class="form-section">
        <h3 class="section-title">
          <span class="icon">🔧</span>
          System Issues
        </h3>
        
        <div class="form-group">
          <label for="transmissionIssues" class="form-label">Transmission Issues</label>
          <select 
            id="transmissionIssues" 
            v-model="store.formData.transmissionIssues"
            class="form-select"
          >
            <option value="">None</option>
            <option value="slipping">Slipping</option>
            <option value="hard_shifts">Hard Shifts</option>
            <option value="delayed_engagement">Delayed Engagement</option>
            <option value="noise">Noise in Transmission</option>
          </select>
        </div>

        <div class="form-group">
          <label for="brakingIssues" class="form-label">Braking Issues</label>
          <select 
            id="brakingIssues" 
            v-model="store.formData.brakingIssues"
            class="form-select"
          >
            <option value="">None</option>
            <option value="soft_pedal">Soft Brake Pedal</option>
            <option value="pulling">Vehicle Pulling</option>
            <option value="vibration">Brake Vibration</option>
            <option value="noise">Brake Noise</option>
          </select>
        </div>
      </div>

      <!-- Multi-select Sections -->
      <div class="form-section">
        <h3 class="section-title">
          <span class="icon">⚠️</span>
          Warning Lights & Other Issues
        </h3>

        <div class="multi-select-grid">
          <div class="form-group">
            <label class="form-label">Warning Lights</label>
            <div class="multi-select">
              <label v-for="light in warningLights" :key="light.value" class="multi-select-item">
                <input 
                  type="checkbox" 
                  :value="light.value"
                  v-model="store.formData.warningLights"
                  class="multi-select-checkbox"
                >
                <span class="multi-select-text">{{ light.label }}</span>
              </label>
            </div>
          </div>

          <div class="form-group">
            <label class="form-label">Electrical Problems</label>
            <div class="multi-select">
              <label v-for="problem in electricalProblems" :key="problem.value" class="multi-select-item">
                <input 
                  type="checkbox" 
                  :value="problem.value"
                  v-model="store.formData.electricalProblems"
                  class="multi-select-checkbox"
                >
                <span class="multi-select-text">{{ problem.label }}</span>
              </label>
            </div>
          </div>

          <div class="form-group">
            <label class="form-label">Unusual Smells</label>
            <div class="multi-select">
              <label v-for="smell in unusualSmells" :key="smell.value" class="multi-select-item">
                <input 
                  type="checkbox" 
                  :value="smell.value"
                  v-model="store.formData.unusualSmells"
                  class="multi-select-checkbox"
                >
                <span class="multi-select-text">{{ smell.label }}</span>
              </label>
            </div>
          </div>

          <div class="form-group">
            <label class="form-label">Fluid Leaks</label>
            <div class="multi-select">
              <label v-for="leak in fluidLeaks" :key="leak.value" class="multi-select-item">
                <input 
                  type="checkbox" 
                  :value="leak.value"
                  v-model="store.formData.fluidLeaks"
                  class="multi-select-checkbox"
                >
                <span class="multi-select-text">{{ leak.label }}</span>
              </label>
            </div>
          </div>
        </div>
      </div>

      <!-- Form Actions -->
      <div class="form-actions">
        <button 
          type="button" 
          @click="store.loadExampleSymptoms"
          class="btn btn-secondary"
          :disabled="store.isLoading"
        >
          Load Example
        </button>
        
        <button 
          type="button" 
          @click="store.resetForm"
          class="btn btn-outline"
          :disabled="store.isLoading"
        >
          Reset Form
        </button>
        
        <button 
          type="submit" 
          :disabled="store.isLoading"
          class="btn btn-primary"
        >
          <span v-if="store.isLoading" class="loading-text">
            <span class="spinner"></span>
            Analyzing...
          </span>
          <span v-else>Diagnose Car</span>
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { useTroubleshooterStore } from '@/store/troubleshooter'

const store = useTroubleshooterStore()

const warningLights = [
  { value: 'check_engine', label: 'Check Engine' },
  { value: 'oil_pressure', label: 'Oil Pressure' },
  { value: 'battery', label: 'Battery' },
  { value: 'abs', label: 'ABS' },
  { value: 'airbag', label: 'Airbag' },
  { value: 'coolant_temp', label: 'Coolant Temperature' },
  { value: 'tire_pressure', label: 'Tire Pressure' },
]

const electricalProblems = [
  { value: 'dead_battery', label: 'Dead Battery' },
  { value: 'dim_lights', label: 'Dim Lights' },
  { value: 'radio_reset', label: 'Radio Reset' },
  { value: 'power_window_issues', label: 'Power Window Issues' },
  { value: 'blown_fuses', label: 'Blown Fuses' },
]

const unusualSmells = [
  { value: 'burning_oil', label: 'Burning Oil' },
  { value: 'coolant', label: 'Sweet Coolant' },
  { value: 'gasoline', label: 'Gasoline' },
  { value: 'rotten_eggs', label: 'Rotten Eggs' },
  { value: 'burning_rubber', label: 'Burning Rubber' },
]

const fluidLeaks = [
  { value: 'oil', label: 'Oil' },
  { value: 'coolant', label: 'Coolant' },
  { value: 'transmission', label: 'Transmission Fluid' },
  { value: 'brake', label: 'Brake Fluid' },
  { value: 'power_steering', label: 'Power Steering Fluid' },
]

const handleSubmit = () => {
  store.submitSymptoms(store.formData)
}
</script>

<style scoped>
.symptom-form {
  padding: 2rem;
  background: white;
}

.form-header {
  text-align: center;
  margin-bottom: 2rem;
}

.form-header h2 {
  color: var(--dark-color);
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
}

.subtitle {
  color: var(--gray-color);
  font-size: 1.1rem;
}

.form-section {
  background: #f8fafc;
  border-radius: var(--border-radius);
  padding: 1.5rem;
  margin-bottom: 1.5rem;
  border: 1px solid #e2e8f0;
  transition: var(--transition);
}

.form-section:hover {
  border-color: var(--primary-color);
  box-shadow: 0 4px 6px rgba(59, 130, 246, 0.1);
}

.section-title {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  color: var(--dark-color);
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
  padding-bottom: 0.75rem;
  border-bottom: 2px solid var(--primary-color);
}

.icon {
  font-size: 1.5rem;
}

.form-group {
  margin-bottom: 1.25rem;
}

.form-label {
  display: block;
  color: var(--dark-color);
  font-weight: 500;
  margin-bottom: 0.5rem;
  font-size: 0.95rem;
}

.required {
  color: var(--danger-color);
}

.form-input,
.form-select {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  font-size: 1rem;
  transition: var(--transition);
  background: white;
}

.form-input:focus,
.form-select:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
}

.grid-2 {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
}

.checkbox-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
  margin-top: 1rem;
}

.checkbox-label {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  cursor: pointer;
  user-select: none;
  padding: 0.5rem;
  border-radius: 6px;
  transition: var(--transition);
}

.checkbox-label:hover {
  background: #edf2f7;
}

.checkbox-input {
  display: none;
}

.checkbox-custom {
  width: 20px;
  height: 20px;
  border: 2px solid #cbd5e0;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: var(--transition);
}

.checkbox-input:checked + .checkbox-custom {
  background: var(--primary-color);
  border-color: var(--primary-color);
}

.checkbox-input:checked + .checkbox-custom::after {
  content: '✓';
  color: white;
  font-size: 14px;
}

.multi-select-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
}

.multi-select {
  background: white;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  padding: 1rem;
  max-height: 200px;
  overflow-y: auto;
}

.multi-select-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.5rem;
  border-radius: 4px;
  transition: var(--transition);
  cursor: pointer;
}

.multi-select-item:hover {
  background: #f7fafc;
}

.multi-select-checkbox {
  width: 16px;
  height: 16px;
}

.multi-select-text {
  font-size: 0.95rem;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 2rem;
  padding-top: 2rem;
  border-top: 2px solid #e2e8f0;
}

.btn {
  padding: 0.875rem 1.75rem;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: var(--transition);
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-primary {
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  min-width: 160px;
}

.btn-primary:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: var(--shadow);
}

.btn-secondary {
  background: #e2e8f0;
  color: var(--dark-color);
}

.btn-secondary:hover:not(:disabled) {
  background: #cbd5e0;
}

.btn-outline {
  background: transparent;
  border: 2px solid var(--primary-color);
  color: var(--primary-color);
}

.btn-outline:hover:not(:disabled) {
  background: var(--primary-color);
  color: white;
}

.loading-text {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.spinner {
  width: 16px;
  height: 16px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  border-top-color: white;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

@media (max-width: 768px) {
  .symptom-form {
    padding: 1rem;
  }
  
  .form-actions {
    flex-direction: column;
  }
  
  .btn {
    width: 100%;
  }
}
</style>