package com.aselsan.domain.model

import com.aselsan.domain.customer.CustomerType
import kotlinx.serialization.Serializable



@Serializable
data class CustomerResponse(
    val id: String,
    val name: String,
    val registeredAt: String,
    val totalSpent: Double,
    val lastYearSpent: Double,
    val type: CustomerType
)
