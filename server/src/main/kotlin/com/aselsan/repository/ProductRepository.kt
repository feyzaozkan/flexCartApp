package com.aselsan.repository
import com.aselsan.utils.CsvDataLoader
import com.aselsan.domain.product.Product
import com.aselsan.domain.product.ProductCategoryType
import java.util.UUID

object ProductRepository {
    private var products = mutableListOf<Product>()

    init {
        val loader = CsvDataLoader()
        val filePath = "/Users/otisvaliant/IdeaProjects/flexCartApp/server/src/main/kotlin/com/aselsan/data/products.csv"
        products = loader.load(filePath) { row ->
            Product(
                id = UUID.fromString(row["id"]),
                name = row["name"] ?: "",
                category = ProductCategoryType.valueOf(row["category"] ?: "OTHERS"),
                price = row["price"]?.toDoubleOrNull() ?: 0.0
            )
        }.toMutableList()
    }

    fun getAll(): List<Product> = products



    fun productById(id: String): Product? {
        val uuidId = UUID.fromString(id)
        /*
        val uuidId = try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            return null
        }
        */

        return products.find { it.id == uuidId }
    }

}