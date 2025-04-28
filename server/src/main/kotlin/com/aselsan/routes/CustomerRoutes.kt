package com.aselsan.routes
import com.aselsan.com.aselsan.domain.model.GenericResponse
import com.aselsan.domain.model.CustomerResponse
import com.aselsan.service.CustomerService
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route


fun Route.customerRoutes(customerService: CustomerService) {
    route("/customers") {
        get("") {
            try {
                val customers: List<CustomerResponse>  = customerService.getAllCustomers()
                call.respond(HttpStatusCode.OK, customers)
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError,
                    GenericResponse(success = false, message = "An unexpected error occurred."
                    ))
            }

        }
        get("/{id}") {
            try {
                val id = call.parameters["id"]
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val customer : CustomerResponse? = customerService.findById(id)
                if (customer == null) {
                    call.respond(HttpStatusCode.NotFound,
                        GenericResponse(success = false, message = "Customer not found."
                        ))
                    return@get
                }
                call.respond(customer)
            }catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError,
                    GenericResponse(success = false, message = "An unexpected error occurred."
                    ))
            }

        }

    }
}

