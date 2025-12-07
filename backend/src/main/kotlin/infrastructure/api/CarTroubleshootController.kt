package infrastructure.api

import domain.models.Symptom
import infrastructure.services.CarTroubleshootService

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class CarTroubleshootController(
    private val service: CarTroubleshootService
) {
    fun configureRoutes(routing: Routing) {
        routing.route("/api/troubleshoot") {
            post {
                try {
                    val symptom = call.receive<Symptom>()
                    val diagnosis = service.diagnose(symptom)
                    call.respond(diagnosis)
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "Invalid request: ${e.message}")
                    )
                }
            }
        }
    }
}