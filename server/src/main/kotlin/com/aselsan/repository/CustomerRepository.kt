package com.aselsan.repository

import com.aselsan.domain.customer.Customer
import com.aselsan.domain.customer.CustomerType
import com.aselsan.utils.CsvDataLoader
import java.text.SimpleDateFormat
import java.util.*

object CustomerRepository {
    private val customers = mutableListOf<Customer>()
    init {
        val loader = CsvDataLoader()

        // Path to the CSV file (relative to the classpath)
        val filePath = "/customers.csv"

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        customers.addAll(
            loader.load(filePath) { row ->
                Customer(
                    id = UUID.fromString(row["id"] ?: ""),
                    name = row["name"] ?: "",
                    registeredAt = row["registeredAt"]?.let { dateFormat.parse(it) } ?: Date(),
                    totalSpent = row["totalSpent"]?.toDoubleOrNull() ?: 0.0,
                    lastYearSpent = row["lastYearSpent"]?.toDoubleOrNull() ?: 0.0,
                    type = CustomerType.valueOf(row["type"] ?: "NEW")
                )
            }
        )
    }

    fun allCustomers(): List<Customer> = customers
    fun customerById(id: String) = customers.find {
        val uuidId = try {
            UUID.fromString(id)
        } catch (e: IllegalArgumentException) {
            return null
        }

        return customers.find { it.id == uuidId }
    }
    fun addSpentToCustomer(customerId: String, amount: Double): Boolean {
        val customer = customerById(customerId) ?: return false
        customer.addSpent(amount)
        return true
    }

}
