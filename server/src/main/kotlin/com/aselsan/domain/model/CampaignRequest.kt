package com.aselsan.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class CartItemRequest(val productId: String, val quantity: Int )

@Serializable
data class CampaignRequest(val customerId: String, val items: List<CartItemRequest> )

@Serializable
data class CompletePaymentRequest(val customerId: String, val amountToPay: Double )