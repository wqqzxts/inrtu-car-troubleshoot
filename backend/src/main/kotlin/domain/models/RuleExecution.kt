package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class RuleExecution(
    val ruleId: String,
    val ruleName: String,
    val condition: String,
    val action: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    companion object {
        @JvmStatic
        fun create(ruleId: String, ruleName: String, condition: String, action: String): RuleExecution {
            return RuleExecution(ruleId, ruleName, condition, action)
        }
    }
}