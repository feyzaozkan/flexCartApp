package com.aselsan.routes
import com.aselsan.com.aselsan.domain.model.GenericResponse
import com.aselsan.domain.model.ProductResponse
import com.aselsan.service.ProductService

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route


fun Route.productRoutes(productService: ProductService) {
    route("/products") {
        get("") {
            try {
                val products: List<ProductResponse> = productService.getAllProducts()
                call.respond(HttpStatusCode.OK, products)
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError,
                    GenericResponse(success = false, message = "An unexpected error occurred."
                    ))
            }

        }
    }
}

