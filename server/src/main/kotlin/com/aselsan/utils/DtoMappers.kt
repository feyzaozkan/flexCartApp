package com.aselsan.utils
import com.aselsan.domain.customer.Customer
import com.aselsan.domain.model.CustomerResponseDto
import com.aselsan.domain.model.ProductResponseDto
import com.aselsan.domain.product.Product

fun Customer.toCustomerResponseDto(): CustomerResponseDto {
    return CustomerResponseDto(
        id = this.id.toString(),
        name = this.name,
        registeredAt = this.registeredAt.toString(),
        totalSpent = this.totalSpent,
        lastYearSpent = this.lastYearSpent,
        type = this.type

    )
}


// Product -> ProductResponseDto
fun Product.toProductResponseDto(): ProductResponseDto {
    return ProductResponseDto(
        id = this.id.toString(),
        name = this.name,
        category = this.category,
        price = this.price
    )
}