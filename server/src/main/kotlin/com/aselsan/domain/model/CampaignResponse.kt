package com.aselsan.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class CampaignResponse(
    val name: String,
    val discount: Double
)