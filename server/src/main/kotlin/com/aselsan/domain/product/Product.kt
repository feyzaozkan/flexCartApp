package com.aselsan.domain.product
import java.util.UUID


class Product(
    val id: UUID,
    val name: String,
    val category: ProductCategoryType,
    val price: Double,
) {

}
