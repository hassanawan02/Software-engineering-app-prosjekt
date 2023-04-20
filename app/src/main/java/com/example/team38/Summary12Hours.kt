package com.example.team38
import kotlinx.serialization.Serializable

@Serializable
data class Summary12Hours(
    val symbol_code: String,
    val symbol_confidence: String
)