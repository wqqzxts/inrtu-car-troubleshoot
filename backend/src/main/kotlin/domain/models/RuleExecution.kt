package domain.models

data class RuleExecution(
    val ruleId: String,
    val ruleName: String,
    val condition: String,
    val action: String,
    val timestamp: Long = System.currentTimeMillis()
)
