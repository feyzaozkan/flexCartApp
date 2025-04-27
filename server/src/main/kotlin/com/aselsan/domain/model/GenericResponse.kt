package com.aselsan.com.aselsan.domain.model
import kotlinx.serialization.Serializable

@Serializable
data class GenericResponse(
    val success: Boolean,
    val message: String
)
