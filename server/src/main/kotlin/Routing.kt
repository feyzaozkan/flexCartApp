package com.aselsan

import com.aselsan.com.aselsan.repository.ShoppingRepository
import com.aselsan.com.aselsan.service.ShoppingService
import com.aselsan.routes.customerRoutes
import com.aselsan.routes.productRoutes
import com.aselsan.routes.shoppingRoutes
import com.aselsan.service.CustomerService
import com.aselsan.service.ProductService
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Post)
        allowHeader(HttpHeaders.Authorization)
        anyHost()
    }

    routing {
        customerRoutes(CustomerService())
        productRoutes(ProductService())
        shoppingRoutes(ShoppingService(ShoppingRepository()))

    }
}
