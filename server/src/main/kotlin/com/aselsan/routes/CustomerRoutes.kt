package com.aselsan.routes
import com.aselsan.domain.model.CustomerResponseDto
import com.aselsan.service.CustomerService
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route


fun Route.customerRoutes(customerService: CustomerService) {
    route("/customers") {
        get("") {
            val customers: List<CustomerResponseDto>  = customerService.getAllCustomers()
            call.respond(HttpStatusCode.OK, customers)
        }
        get("/{id}") {
            val id = call.parameters["id"]

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val customer : CustomerResponseDto? = customerService.findById(id)
            if (customer == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }

            call.respond(customer)
        }

    }
}

