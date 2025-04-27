package com.aselsan.service
import  com.aselsan.domain.model.ProductResponse
import  com.aselsan.repository.ProductRepository
import  com.aselsan.utils.toProductResponseDto


class ProductService {
    fun getAllProducts(): List<ProductResponse> {
        val products = ProductRepository.getAll()
        return products.map { it.toProductResponseDto() }
    }

}