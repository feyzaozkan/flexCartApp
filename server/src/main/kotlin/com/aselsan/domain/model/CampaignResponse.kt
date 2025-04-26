package com.aselsan.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class CampaignDetails(
    val name: String,
    val discount: Double
)

@Serializable
data class CampaignResponse(
    val campaignDetails: List<CampaignDetails>,
    val shippingFee: Double = 25.0
)