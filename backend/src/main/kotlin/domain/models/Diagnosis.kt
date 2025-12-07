package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Diagnosis(
    var problem: CarProblem,
    var confidence: Double,
    var matchedSymptoms: List<String>,
    val ruleChain: List<RuleExecution>,
    val timestamp: Long = System.currentTimeMillis()
)