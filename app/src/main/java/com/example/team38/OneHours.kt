package com.example.team38
import kotlinx.serialization.Serializable

@Serializable
data class OneHours (
    val summary: Summary,
    val details: OneDetails
)