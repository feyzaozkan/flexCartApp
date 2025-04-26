package com.aselsan.service
import  com.aselsan.domain.model.ProductResponseDto
import  com.aselsan.repository.ProductRepository
import  com.aselsan.utils.toProductResponseDto


class ProductService {
    fun getAllProducts(): List<ProductResponseDto> {
        val products = ProductRepository.getAll()
        return products.map { it.toProductResponseDto() }
    }

}