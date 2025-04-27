package com.aselsan.utils
import com.aselsan.domain.customer.Customer
import com.aselsan.domain.model.CustomerResponse
import com.aselsan.domain.model.ProductResponse
import com.aselsan.domain.product.Product

fun Customer.toCustomerResponseDto(): CustomerResponse {
    return CustomerResponse(
        id = this.id.toString(),
        name = this.name,
        registeredAt = this.registeredAt.toString(),
        totalSpent = this.totalSpent,
        lastYearSpent = this.lastYearSpent,
        type = this.type

    )
}


// Product -> ProductResponseDto
fun Product.toProductResponseDto(): ProductResponse {
    return ProductResponse(
        id = this.id.toString(),
        name = this.name,
        category = this.category,
        price = this.price
    )
}