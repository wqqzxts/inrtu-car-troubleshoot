package domain.models

import domain.enums.EngineType

import kotlinx.serialization.Serializable

@Serializable
data class Symptom(
    val engineType: EngineType,
    val engineNoise: String? = null,
    val startingIssue: Boolean = false,
    val stalling: Boolean = false,
    val overheating: Boolean = false,
    val smokeFromExhaust: String? = null,
    val warningLights: List<String> = emptyList(),
    val fuelConsumptionIncreased: Boolean = false,
    val lossOfPower: Boolean = false,
    val roughIdling: Boolean = false,
    val transmissionIssues: String? = null,
    val brakingIssues: String? = null,
    val electricalProblems: List<String> = emptyList(),
    val mileage: Int,
    val lastServiceMonths: Int,
    val unusualSmells: List<String> = emptyList(),
    val fluidLeaks: List<String> = emptyList()
) {
    fun hasWarningLight(light: String): Boolean {
        return warningLights.any { it.equals(light, ignoreCase = true) }
    }

    fun hasFluidLeak(color: String): Boolean {
        return fluidLeaks.any { it.contains(color, ignoreCase = true) }
    }

    fun hasUnusualSmell(smell: String): Boolean {
        return unusualSmells.any { it.contains(smell, ignoreCase = true) }
    }
}