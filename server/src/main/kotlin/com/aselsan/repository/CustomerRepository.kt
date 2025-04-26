package com.aselsan.repository

import com.aselsan.domain.customer.Customer
import com.aselsan.domain.customer.CustomerType
import java.util.*

object CustomerRepository {
    private val customers = mutableListOf<Customer>()
    init {
        val now = Date()
        customers.add(
            Customer(UUID.randomUUID(), "Alice", now, 6000.0, 5500.0, CustomerType.PREMIUM)
        )
        customers.add(
            Customer(UUID.randomUUID(), "Bob", now, 300.0, 100.0, CustomerType.NEW)
        )
        customers.add(
            Customer(UUID.randomUUID(), "Charlie", now, 1000.0, 200.0, CustomerType.STANDARD)
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
}
