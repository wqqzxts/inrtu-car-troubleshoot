package infrastructure.services

import domain.enums.ProblemCategory
import domain.enums.Severity
import domain.models.CarProblem
import domain.models.Diagnosis
import domain.models.Symptom
import infrastructure.rules.DroolsConfig
import org.kie.api.runtime.KieSession

class CarTroubleshootService {
    private val droolsConfig = DroolsConfig()

    fun diagnose(symptom: Symptom): Diagnosis {
        val diagnosis = Diagnosis(
            problem = CarProblem(
                id = "P000",
                name = "Unknown Problem",
                description = "Diagnosis in progress",
                category = ProblemCategory.ENGINE,
                severity = Severity.LOW,
                possibleCauses = emptyList(),
                solutions = emptyList()
            ),
            confidence = 0.0,
            matchedSymptoms = mutableListOf(),
            ruleChain = mutableListOf()
        )

        val session: KieSession = droolsConfig.newSession()

        try {
            session.insert(symptom)
            session.insert(diagnosis)

            session.fireAllRules()

            // collect problems provided by rules
            val problems = session.getObjects { obj -> obj is CarProblem && obj.id != "P000" }
                .map { it as CarProblem }
                .toList()

            if (problems.isNotEmpty()) {
                // baddest problem
                val primaryProblem = problems.maxByOrNull {
                    Severity.entries.indexOf(it.severity)
                } ?: problems.first()

                diagnosis.problem = primaryProblem
                diagnosis.confidence = calculateConfidence(symptom, primaryProblem)
                diagnosis.matchedSymptoms = extractMatchedSymptoms(symptom)
            }

        } finally {
            session.dispose()
        }

        return diagnosis
    }

    private fun calculateConfidence(symptom: Symptom, problem: CarProblem): Double {
        var score = 0.0

        when (problem.severity) {
            Severity.CRITICAL -> score += 40
            Severity.HIGH -> score += 30
            Severity.MEDIUM -> score += 20
            Severity.LOW -> score += 10
        }

        return minOf(score, 100.0)
    }

    private fun extractMatchedSymptoms(symptom: Symptom): List<String> {
        val matched = mutableListOf<String>()

        if (symptom.startingIssue) matched.add("Starting Issue")
        if (symptom.overheating) matched.add("Overheating")
        if (symptom.stalling) matched.add("Stalling")
        if (symptom.lossOfPower) matched.add("Loss of Power")
        if (symptom.roughIdling) matched.add("Rough Idling")
        if (symptom.fuelConsumptionIncreased) matched.add("Increased Fuel Consumption")

        symptom.warningLights.forEach { matched.add("$it Warning Light") }
        symptom.unusualSmells.forEach { matched.add("$it Smell") }
        symptom.fluidLeaks.forEach { matched.add("$it Fluid Leak") }

        symptom.engineNoise?.let { matched.add("$it Noise") }
        symptom.transmissionIssues?.let { matched.add("Transmission: $it") }
        symptom.brakingIssues?.let { matched.add("Braking: $it") }
        symptom.smokeFromExhaust?.let { matched.add("$it Exhaust Smoke") }

        return matched
    }
}