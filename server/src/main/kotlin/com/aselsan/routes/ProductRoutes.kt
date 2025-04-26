package com.aselsan.routes
import com.aselsan.service.ProductService

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route


fun Route.productRoutes(productService: ProductService) {
    route("/products") {
        get("") {
            val products = productService.getAllProducts()
            call.respond(HttpStatusCode.OK, products)
        }


    }
}

