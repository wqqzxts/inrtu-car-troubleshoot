package domain.models

import domain.enums.ProblemCategory
import domain.enums.Severity

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
)
