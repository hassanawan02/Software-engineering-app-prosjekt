package com.example.team38
import kotlinx.serialization.Serializable

@Serializable
data class TwelveHours (
    val summary: Summary,
    val details: Details12Hours
)