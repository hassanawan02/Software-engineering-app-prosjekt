package com.example.team38
import kotlinx.serialization.Serializable

@Serializable
data class SixHours (
    val summary: Summary,
    val details: SixDetails
)