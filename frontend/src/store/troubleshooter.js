import { defineStore } from 'pinia'
import { troubleshootCar } from '@/api/expertSystemApi'

export const useTroubleshooterStore = defineStore('troubleshooter', {
  state: () => ({
    diagnosis: null,
    isLoading: false,
    error: null,
    formData: {
      engineType: 'GASOLINE',
      engineNoise: '',
      startingIssue: false,
      stalling: false,
      overheating: false,
      smokeFromExhaust: '',
      warningLights: [],
      fuelConsumptionIncreased: false,
      lossOfPower: false,
      roughIdling: false,
      transmissionIssues: '',
      brakingIssues: '',
      mileage: 0,
      unusualSmells: [],
      fluidLeaks: [],      
    },
    symptomsHistory: [],
  }),

  actions: {
    async submitSymptoms(symptoms) {
      this.isLoading = true
      this.error = null
      
      try {        
        const { electricalProblems, lastServiceMonths, ...filteredSymptoms } = symptoms
        
        const diagnosis = await troubleshootCar(filteredSymptoms)
        this.diagnosis = diagnosis
        this.symptomsHistory.unshift({
          timestamp: new Date().toISOString(),
          symptoms: { ...filteredSymptoms },
          diagnosis: diagnosis.problem.name
        })
      } catch (error) {
        this.error = error.message
        console.error('Diagnosis error:', error)
      } finally {
        this.isLoading = false
      }
    },

    resetForm() {
      this.formData = {
        engineType: 'GASOLINE',
        engineNoise: '',
        startingIssue: false,
        stalling: false,
        overheating: false,
        smokeFromExhaust: '',
        warningLights: [],
        fuelConsumptionIncreased: false,
        lossOfPower: false,
        roughIdling: false,
        transmissionIssues: '',
        brakingIssues: '',
        mileage: 0,
        unusualSmells: [],
        fluidLeaks: [],
      }
      this.diagnosis = null
      this.error = null
    },

    loadExampleSymptoms() {
      this.formData = {
        engineType: 'GASOLINE',
        engineNoise: 'knocking',
        startingIssue: true,
        stalling: false,
        overheating: true,
        smokeFromExhaust: 'white',
        warningLights: ['check_engine', 'oil_pressure'],
        fuelConsumptionIncreased: true,
        lossOfPower: true,
        roughIdling: true,
        transmissionIssues: '',
        brakingIssues: '',
        mileage: 75000,
        unusualSmells: ['burning_oil'],
        fluidLeaks: ['oil'],
      }
    },
  },
})