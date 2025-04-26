package com.aselsan.domain.model



import kotlinx.serialization.Serializable

@Serializable
data class CustomerRequest(val name: String)