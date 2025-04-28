package com.aselsan.routes
import com.aselsan.com.aselsan.domain.model.GenericResponse
import com.aselsan.com.aselsan.service.ShoppingService
import com.aselsan.domain.model.CampaignRequest
import com.aselsan.domain.model.CampaignResponse
import com.aselsan.domain.model.CompletePaymentRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.request.ContentTransformationException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route


fun Route.shoppingRoutes(shoppingService: ShoppingService) {
    route("/shopping") {
        post("/applyDiscount") {
            try {
                val request = call.receive<CampaignRequest>()
                val response : CampaignResponse = shoppingService.applyDiscount(request)
                call.respond(status = HttpStatusCode.OK, response)
            }
            catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.NotFound,
                    GenericResponse(success = false, message = e.message ?: "Invalid request")
                )
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError,
                    GenericResponse(success = false, message = "An unexpected error occurred."
                    ))
            }

        }

        patch("/completePayment") {
            try {
                val request = call.receive<CompletePaymentRequest>()

                if (request.amountToPay <= 0) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        GenericResponse(success = false, message = "Amount to pay must be greater than 0.")
                    )
                    return@patch
                }

                val response : Boolean = shoppingService.completePayment(request)
                if (response) {
                    call.respond(HttpStatusCode.OK,GenericResponse(success = true, message = "Payment completed successfully.") )
                } else {
                    call.respond(HttpStatusCode.BadRequest,GenericResponse(success = false, message = "Payment could not be completed" ))
                }
            }
            catch (e: BadRequestException) {
                call.respond(HttpStatusCode.BadRequest, message = GenericResponse(success = false, message = "Payment could not be completed"))
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError,
                    GenericResponse(success = false, message = "An unexpected error occurred."
                    ))
            }

        }

    }
}
