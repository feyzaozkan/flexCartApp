package com.aselsan.domain.customer
import java.util.UUID
import java.util.Date


class Customer(
    val id: UUID,
    val name: String,
    val registeredAt: Date,
    val totalSpent: Double,
    val lastYearSpent: Double,
    val type: CustomerType
)
{
}
