package com.aselsan.routes
import com.aselsan.com.aselsan.service.ShoppingService
import com.aselsan.domain.model.CampaignRequest
import com.aselsan.domain.model.CampaignResponse
import com.aselsan.domain.model.CompletePaymentRequest


import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route


fun Route.shoppingRoutes(shoppingService: ShoppingService) {
    route("/shopping") {
        post("/applyDiscount") {
            val request = call.receive<CampaignRequest>()
            val response : CampaignResponse = shoppingService.applyDiscount(request)
            call.respond(status = HttpStatusCode.OK, response)
        }
        patch("/completePayment") {
            val request = call.receive<CompletePaymentRequest>()
            val response : Boolean = shoppingService.completePayment(request)
            if (response) {
                call.respond(HttpStatusCode.OK, "Payment completed successfully.")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Payment could not be completed.")
            }
        }

    }
}
