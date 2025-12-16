<template>
  <div class="symptom-form">
    <!-- Validation Errors -->
    <div v-if="validationErrors.length > 0" class="validation-errors">
      <div class="error-header">        
        <h3>Invalid Symptom Combination</h3>
      </div>
      <ul class="error-list">
        <li v-for="(error, index) in validationErrors" :key="index">
          {{ error }}
        </li>
      </ul>
      <button @click="validationErrors = []" class="btn-error-close">
        Dismiss
      </button>
    </div>

    <form @submit.prevent="handleSubmit" class="form-grid">
      <!-- Basic Information Section -->
      <div class="form-section">
        <h3 class="section-title">
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
              @change="onEngineTypeChange"
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
            />
          </div>
        </div>
      </div>

      <!-- Engine Symptoms Section -->
      <div class="form-section">
        <h3 class="section-title">          
          Engine Symptoms
        </h3>
        <div class="grid-2">
          <div class="form-group">
            <label for="engineNoise" class="form-label">Engine Noise</label>
            <select
              id="engineNoise"
              v-model="store.formData.engineNoise"
              class="form-select"
              :disabled="isElectric"
            >
              <option value="">None</option>
              <option value="knocking">Knocking</option>
              <option value="tapping">Tapping</option>
              <option value="hissing">Hissing</option>              
              <option value="grinding">Grinding</option>
            </select>
            <small v-if="isElectric" class="field-disabled-hint">
              Electric motors don't have typical engine noises
            </small>
          </div>

          <div class="form-group">
            <label for="smokeFromExhaust" class="form-label"
              >Exhaust Smoke</label
            >
            <select
              id="smokeFromExhaust"
              v-model="store.formData.smokeFromExhaust"
              class="form-select"
              :disabled="isElectric || isHybrid"
            >
              <option value="">None</option>
              <option value="white">White Smoke</option>
              <option value="blue">Blue Smoke</option>
              <option value="black">Black Smoke</option>
            </select>
            <small v-if="isElectric" class="field-disabled-hint">
              Electric vehicles don't have exhaust systems
            </small>
            <small v-if="isHybrid" class="field-disabled-hint">
              Hybrid vehicles can still produce exhaust when using combustion
              engine
            </small>
          </div>
        </div>

        <div class="checkbox-grid">
          <label class="checkbox-label">
            <input
              type="checkbox"
              v-model="store.formData.startingIssue"
              class="checkbox-input"
              :disabled="isElectric"
            />
            <span class="checkbox-custom"></span>
            <span>Starting Issues</span>
            <span v-if="isElectric" class="checkbox-disabled-note"
              >(N/A for EV)</span
            >
          </label>

          <label class="checkbox-label">
            <input
              type="checkbox"
              v-model="store.formData.stalling"
              class="checkbox-input"
              :disabled="isElectric"
            />
            <span class="checkbox-custom"></span>
            <span>Stalling</span>
            <span v-if="isElectric" class="checkbox-disabled-note"
              >(N/A for EV)</span
            >
          </label>

          <label class="checkbox-label">
            <input
              type="checkbox"
              v-model="store.formData.overheating"
              class="checkbox-input"
            />
            <span class="checkbox-custom"></span>
            <span>Overheating</span>
          </label>

          <label class="checkbox-label">
            <input
              type="checkbox"
              v-model="store.formData.fuelConsumptionIncreased"
              class="checkbox-input"
              :disabled="isElectric"
            />
            <span class="checkbox-custom"></span>
            <span>Increased Fuel Consumption</span>
            <span v-if="isElectric" class="checkbox-disabled-note"
              >(N/A for EV)</span
            >
          </label>

          <label class="checkbox-label">
            <input
              type="checkbox"
              v-model="store.formData.lossOfPower"
              class="checkbox-input"
            />
            <span class="checkbox-custom"></span>
            <span>Loss of Power</span>
          </label>

          <label class="checkbox-label">
            <input
              type="checkbox"
              v-model="store.formData.roughIdling"
              class="checkbox-input"
              :disabled="isElectric"
            />
            <span class="checkbox-custom"></span>
            <span>Rough Idling</span>
            <span v-if="isElectric" class="checkbox-disabled-note"
              >(N/A for EV)</span
            >
          </label>
        </div>
      </div>

      <!-- System Issues Section -->
      <div class="form-section">
        <h3 class="section-title">
          System Issues
        </h3>

        <div class="form-group">
          <label for="transmissionIssues" class="form-label"
            >Transmission Issues</label
          >
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
          Warning Lights & Other Issues
        </h3>

        <div class="multi-select-grid">
          <div class="form-group">
            <label class="form-label">Warning Lights</label>
            <div class="multi-select">
              <label
                v-for="light in filteredWarningLights"
                :key="light.value"
                class="multi-select-item"
              >
                <input
                  type="checkbox"
                  :value="light.value"
                  v-model="store.formData.warningLights"
                  class="multi-select-checkbox"
                  :disabled="isElectric && light.value === 'oil_pressure'"
                />
                <span class="multi-select-text">{{ light.label }}</span>
                <span
                  v-if="isElectric && light.value === 'oil_pressure'"
                  class="multi-select-disabled"
                >
                  (N/A)
                </span>
              </label>
            </div>
          </div>

          <div class="form-group">
            <label class="form-label">Unusual Smells</label>
            <div class="multi-select">
              <label
                v-for="smell in filteredUnusualSmells"
                :key="smell.value"
                class="multi-select-item"
              >
                <input
                  type="checkbox"
                  :value="smell.value"
                  v-model="store.formData.unusualSmells"
                  class="multi-select-checkbox"
                  :disabled="
                    isElectric &&
                    ['burning_oil', 'gasoline', 'rotten_eggs'].includes(
                      smell.value
                    )
                  "
                />
                <span class="multi-select-text">{{ smell.label }}</span>
                <span
                  v-if="
                    isElectric &&
                    ['burning_oil', 'gasoline'].includes(smell.value)
                  "
                  class="multi-select-disabled"
                >
                  (N/A)
                </span>
              </label>
            </div>
          </div>

          <div class="form-group">
            <label class="form-label">Fluid Leaks</label>
            <div class="multi-select">
              <label
                v-for="leak in filteredFluidLeaks"
                :key="leak.value"
                class="multi-select-item"
              >
                <input
                  type="checkbox"
                  :value="leak.value"
                  v-model="store.formData.fluidLeaks"
                  class="multi-select-checkbox"
                  :disabled="
                    isElectric &&
                    leak.value !== 'coolant' &&
                    leak.value !== 'brake'
                  "
                />
                <span class="multi-select-text">{{ leak.label }}</span>
                <span
                  v-if="
                    isElectric &&
                    leak.value !== 'coolant' &&
                    leak.value !== 'brake'
                  "
                  class="multi-select-disabled"
                >
                  (N/A)
                </span>
              </label>
            </div>
          </div>
        </div>
      </div>

      <!-- Form Actions -->
      <div class="form-actions">
        <button
          type="button"
          @click="loadExampleWithValidation"
          class="btn btn-secondary"
          :disabled="store.isLoading"
        >
          Load Example
        </button>

        <button
          type="button"
          @click="resetForm"
          class="btn btn-outline"
          :disabled="store.isLoading"
        >
          Reset Form
        </button>

        <button
          type="submit"
          :disabled="store.isLoading || validationErrors.length > 0"
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
import { useTroubleshooterStore } from "@/store/troubleshooter";
import { ref, computed, watch } from "vue";

const store = useTroubleshooterStore();
const validationErrors = ref([]);

const isElectric = computed(() => store.formData.engineType === "ELECTRIC");
const isHybrid = computed(() => store.formData.engineType === "HYBRID");

const warningLights = [
  { value: "check_engine", label: "Check Engine" },
  { value: "oil_pressure", label: "Oil Pressure" },
  { value: "tire_pressure", label: "Tire Pressure" },
];

const electricalProblems = [
  { value: "dead_battery", label: "Dead Battery" },
  { value: "dim_lights", label: "Dim Lights" },
  { value: "radio_reset", label: "Radio Reset" },
  { value: "power_window_issues", label: "Power Window Issues" },
  { value: "blown_fuses", label: "Blown Fuses" },
];

const unusualSmells = [
  { value: "burning_oil", label: "Burning Oil" },
  { value: "rotten_egg", label: "Rotten Eggs" },
  { value: "sweet", label: "Sweet" },
];

const fluidLeaks = [
  { value: "oil", label: "Oil" },
  { value: "green", label: "Coolant" },
  { value: "yellow", label: "Brake Fluid" },  
];

const filteredWarningLights = computed(() => {
  if (isElectric.value) {
    return warningLights.filter((light) => light.value !== "oil_pressure");
  }
  return warningLights;
});

const filteredUnusualSmells = computed(() => {
  if (isElectric.value) {
    return unusualSmells.filter(
      (smell) =>
        !["burning_oil", "gasoline", "rotten_eggs"].includes(smell.value)
    );
  }
  return unusualSmells;
});

const filteredFluidLeaks = computed(() => {
  if (isElectric.value) {
    return fluidLeaks.filter(
      (leak) => leak.value === "coolant" || leak.value === "brake"
    );
  }
  return fluidLeaks;
});

const validateForm = () => {
  const errors = [];
  const form = store.formData;

  if (isElectric.value) {
    if (form.smokeFromExhaust) {
      errors.push("Electric vehicles do not have exhaust systems");
    }

    if (form.unusualSmells.includes("burning_oil")) {
      errors.push("Electric vehicles do not use engine oil");
    }

    if (form.fluidLeaks.includes("oil")) {
      errors.push("Electric vehicles do not have engine oil");
    }

    if (form.fluidLeaks.includes("transmission")) {
      errors.push(
        "Most electric vehicles use single-speed transmissions without traditional fluid"
      );
    }

    if (form.fluidLeaks.includes("power_steering")) {
      errors.push("Most electric vehicles use electric power steering");
    }

    if (form.startingIssue) {
      errors.push(
        "Electric vehicles don't have traditional engine starting systems"
      );
    }

    if (form.stalling) {
      errors.push("Electric vehicles don't stall like combustion engines");
    }

    if (form.fuelConsumptionIncreased) {
      errors.push("Electric vehicles don't use fuel");
    }

    if (form.roughIdling) {
      errors.push("Electric vehicles don't idle");
    }

    if (form.warningLights.includes("oil_pressure")) {
      errors.push("Electric vehicles don't have oil pressure systems");
    }
  }

  if (isHybrid.value) {
  }

  if (form.mileage < 0 || form.mileage > 500000) {
    errors.push("Mileage must be between 0 and 500,000 km");
  }

  if (form.lastServiceMonths < 0 || form.lastServiceMonths > 120) {
    errors.push("Last service must be between 0 and 120 months ago");
  }

  validationErrors.value = errors;
  return errors.length === 0;
};

watch(
  () => store.formData,
  () => {
    validateForm();
  },
  { deep: true }
);

const onEngineTypeChange = () => {
  if (isElectric.value) {
    store.formData.smokeFromExhaust = "";
    store.formData.engineNoise = "";
    store.formData.startingIssue = false;
    store.formData.stalling = false;
    store.formData.fuelConsumptionIncreased = false;
    store.formData.roughIdling = false;

    store.formData.unusualSmells = store.formData.unusualSmells.filter(
      (smell) => !["burning_oil", "gasoline", "rotten_eggs"].includes(smell)
    );

    store.formData.fluidLeaks = store.formData.fluidLeaks.filter(
      (leak) => !["oil", "transmission", "power_steering"].includes(leak)
    );

    store.formData.warningLights = store.formData.warningLights.filter(
      (light) => light !== "oil_pressure"
    );
  }

  validateForm();
};

const handleSubmit = () => {
  if (validateForm()) {
    store.submitSymptoms(store.formData);
  } else {
    setTimeout(() => {
      const errorElement = document.querySelector(".validation-errors");
      if (errorElement) {
        errorElement.scrollIntoView({ behavior: "smooth", block: "start" });
      }
    }, 100);
  }
};

const loadExampleWithValidation = () => {
  store.loadExampleSymptoms();

  setTimeout(() => {
    validateForm();
    if (validationErrors.value.length > 0) {
      console.warn(
        "Example contains invalid combinations for selected engine type"
      );
    }
  }, 100);
};

const resetForm = () => {
  store.resetForm();
  validationErrors.value = [];
};
</script>

<style scoped>
.validation-errors {
  background: linear-gradient(135deg, #fff5f5, #fed7d7);
  border: 2px solid #fc8181;
  border-radius: var(--border-radius);
  padding: 1.5rem;
  margin-bottom: 2rem;
  animation: slideIn 0.3s ease;
}

.error-header {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  margin-bottom: 1rem;
}

.error-header h3 {
  color: #c53030;
  font-size: 1.25rem;
  font-weight: 600;
  margin: 0;
}

.error-icon {
  font-size: 1.5rem;
}

.error-list {
  color: #742a2a;
  margin: 0 0 1rem 0;
  padding-left: 1.5rem;
}

.error-list li {
  margin-bottom: 0.5rem;
  line-height: 1.5;
}

.btn-error-close {
  background: #c53030;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  font-weight: 500;
  cursor: pointer;
  transition: var(--transition);
}

.btn-error-close:hover {
  background: #9b2c2c;
}

.field-disabled-hint {
  display: block;
  color: #718096;
  font-size: 0.85rem;
  margin-top: 0.25rem;
  font-style: italic;
}

.checkbox-disabled-note {
  color: #a0aec0;
  font-size: 0.85rem;
  margin-left: 0.25rem;
}

.multi-select-disabled {
  color: #a0aec0;
  font-size: 0.85rem;
  margin-left: auto;
  font-style: italic;
}

.form-select:disabled,
.checkbox-input:disabled + .checkbox-custom {
  opacity: 0.6;
  cursor: not-allowed;
  background-color: #f7fafc;
}

.checkbox-label
  input:disabled
  ~ span:not(.checkbox-custom):not(.checkbox-disabled-note) {
  opacity: 0.6;
}

.multi-select-item input:disabled ~ .multi-select-text {
  opacity: 0.6;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

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
  content: "✓";
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
  background: linear-gradient(
    135deg,
    var(--primary-color),
    var(--secondary-color)
  );
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