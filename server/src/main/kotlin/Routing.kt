package com.aselsan

import com.aselsan.com.aselsan.repository.ShoppingRepository
import com.aselsan.com.aselsan.service.ShoppingService
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import com.aselsan.routes.customerRoutes
import com.aselsan.routes.productRoutes
import com.aselsan.routes.shoppingRoutes
import com.aselsan.service.CustomerService
import com.aselsan.service.ProductService

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")
        get("/") {
            call.respondText("Hello World!")
        }
        customerRoutes(CustomerService())
        productRoutes(ProductService())
        shoppingRoutes(ShoppingService(ShoppingRepository()))

    }
}
