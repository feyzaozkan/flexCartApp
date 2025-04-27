package com.aselsan.domain.model
import com.aselsan.domain.product.ProductCategoryType
import kotlinx.serialization.Serializable


@Serializable
data class ProductResponse(
    val id: String,
    val name: String,
    val category: ProductCategoryType,
    val price: Double
)
