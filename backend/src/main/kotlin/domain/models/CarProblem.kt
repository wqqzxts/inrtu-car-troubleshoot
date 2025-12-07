package domain.models

import domain.enums.ProblemCategory
import domain.enums.Severity
import kotlinx.serialization.Serializable

@Serializable
data class CarProblem(
    val id: String,
    val name: String,
    val description: String,
    val category: ProblemCategory,
    val severity: Severity,
    val possibleCauses: List<String>,
    val solutions: List<String>,
    val estimatedRepairCost: Double? = null,
    val estimatedRepairTime: String? = null
) {
    companion object {
        @JvmStatic
        fun create(
            id: String,
            name: String,
            description: String,
            category: ProblemCategory,
            severity: Severity,
            possibleCauses: Array<String>,
            solutions: Array<String>,
            estimatedRepairCost: Double? = null,
            estimatedRepairTime: String? = null
        ): CarProblem {
            return CarProblem(
                id, name, description, category, severity,
                possibleCauses.toList(), solutions.toList(),
                estimatedRepairCost, estimatedRepairTime
            )
        }
    }
}