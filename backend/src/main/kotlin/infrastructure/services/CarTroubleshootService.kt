package infrastructure.services

import domain.enums.ProblemCategory
import domain.enums.Severity
import domain.models.CarProblem
import domain.models.Diagnosis
import domain.models.Symptom
import infrastructure.rules.DroolsConfig
import org.kie.api.runtime.KieSession
import java.util.concurrent.ConcurrentHashMap

class CarTroubleshootService {
    private val droolsConfig = DroolsConfig()

    private val warningLightMapping = mapOf(
        "oil_pressure" to "Oil Pressure",
        "check_engine" to "Check Engine",
        "battery" to "Battery",
        "abs" to "ABS",
        "airbag" to "Airbag",
        "tire_pressure" to "Tire Pressure"
    )

    private val engineNoiseMapping = mapOf(
        "knocking" to "Knocking",
        "tapping" to "Tapping",
        "rattling" to "Rattling",
        "clicking" to "Clicking",
        "hissing" to "Hissing",
        "whining" to "Whining",
        "squealing" to "Squealing",
        "grinding" to "Grinding",
        "groaning" to "Groaning",
        "humming" to "Humming",
        "growling" to "Growling",
        "clunking" to "Clunking"
    )

    private val fluidLeaks = mapOf(
        "knocking" to "Knocking",
        "tapping" to "Tapping",
        "rattling" to "Rattling",
        "clicking" to "Clicking",
        "hissing" to "Hissing",
        "whining" to "Whining",
        "squealing" to "Squealing",
        "grinding" to "Grinding",
        "groaning" to "Groaning",
        "humming" to "Humming",
        "growling" to "Growling",
        "clunking" to "Clunking"
    )

    fun diagnose(symptom: Symptom): Diagnosis {
        val normalizedSymptom = normalizeSymptom(symptom)

        val session: KieSession = droolsConfig.newSession()
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

        try {
            val problemsMap = ConcurrentHashMap<String, CarProblem>()
            session.setGlobal("problemsMap", problemsMap)

            session.insert(normalizedSymptom)
            session.insert(diagnosis)

            session.fireAllRules()

            val allProblems = problemsMap.values.toList()

            if (allProblems.isNotEmpty()) {
                val primaryProblem = allProblems.maxByOrNull { problem ->
                    val severityScore = when (problem.severity) {
                        Severity.CRITICAL -> 4
                        Severity.HIGH -> 3
                        Severity.MEDIUM -> 2
                        Severity.LOW -> 1
                    }
                    severityScore
                } ?: allProblems.first()

                diagnosis.problem = primaryProblem
                diagnosis.confidence = calculateConfidence(normalizedSymptom, primaryProblem, allProblems.size)
                diagnosis.matchedSymptoms = extractMatchedSymptoms(normalizedSymptom)

                if (allProblems.size > 1) {
                    val additionalProblems = allProblems
                        .filter { it.id != primaryProblem.id }
                        .joinToString(", ") { it.name }

                    diagnosis.problem = diagnosis.problem.copy(
                        description = "${primaryProblem.description}. Additional potential issues: $additionalProblems"
                    )
                }
            }

        } finally {
            session.dispose()
        }

        return diagnosis
    }

    private fun normalizeSymptom(symptom: Symptom): Symptom {
        val normalizedWarningLights = symptom.warningLights.map {
            warningLightMapping[it] ?: it.replace("_", " ").split(" ").joinToString(" ") { word ->
                word.replaceFirstChar { char -> char.uppercase() }
            }
        }

        val normalizedEngineNoise = symptom.engineNoise?.let {
            engineNoiseMapping[it.lowercase()] ?: it.replaceFirstChar { char -> char.uppercase() }
        }

        val normalizedSmoke = symptom.smokeFromExhaust?.takeIf { it.isNotEmpty() }?.let {
            when (it.lowercase()) {
                "blue" -> "Blue"
                "white" -> "White"
                "black" -> "Black"
                "gray" -> "Gray"
                else -> it.replaceFirstChar { char -> char.uppercase() }
            }
        }

        val normalizedUnusualSmells = symptom.unusualSmells.map {
            when (it.lowercase()) {
                "burning_oil" -> "Burning Oil"
                "rotten_egg" -> "Rotten Egg"
                "sweet" -> "Sweet"
                else -> it.replace("_", " ").split(" ").joinToString(" ") { word ->
                    word.replaceFirstChar { char -> char.uppercase() }
                }
            }
        }

        val normalizedFluidLeaks = symptom.fluidLeaks.map {
            when (it.lowercase()) {
                "oil" -> "Brown"
                "green", "orange" -> it.capitalize()
                "red" -> "Red"
                "clear", "yellow" -> it.capitalize()
                else -> it.capitalize()
            }
        }

        return symptom.copy(
            warningLights = normalizedWarningLights,
            engineNoise = normalizedEngineNoise,
            smokeFromExhaust = normalizedSmoke,
            unusualSmells = normalizedUnusualSmells,
            fluidLeaks = normalizedFluidLeaks
        )
    }

    private fun calculateConfidence(symptom: Symptom, problem: CarProblem, matchedRules: Int): Double {
        var score = 0.0

        when (problem.severity) {
            Severity.CRITICAL -> score += 40
            Severity.HIGH -> score += 30
            Severity.MEDIUM -> score += 20
            Severity.LOW -> score += 10
        }

        score += (matchedRules * 10)

        val symptomCount = countRelevantSymptoms(symptom, problem)
        score += (symptomCount * 5)

        return minOf(score, 100.0)
    }

    private fun countRelevantSymptoms(symptom: Symptom, problem: CarProblem): Int {
        var count = 0

        when {
            symptom.engineNoise?.contains("Knocking") == true -> count++
            symptom.warningLights.isNotEmpty() -> count++
            symptom.fluidLeaks.isNotEmpty() -> count++
            symptom.unusualSmells.isNotEmpty() -> count++
            symptom.smokeFromExhaust?.isNotEmpty() == true -> count++
        }

        return count
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
        symptom.transmissionIssues?.takeIf { it.isNotEmpty() }?.let { matched.add("Transmission: $it") }
        symptom.brakingIssues?.takeIf { it.isNotEmpty() }?.let { matched.add("Braking: $it") }
        symptom.smokeFromExhaust?.takeIf { it.isNotEmpty() }?.let { matched.add("$it Exhaust Smoke") }

        return matched
    }
}