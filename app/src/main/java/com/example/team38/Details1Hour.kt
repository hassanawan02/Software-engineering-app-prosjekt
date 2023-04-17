package com.example.team38
import kotlinx.serialization.Serializable

@Serializable
data class Details1Hour(
    val precipitation_amount: Float,
    val precipitation_amount_max: Float,
    val precipitation_amount_min: Float,
    val probability_of_precipitation: Float,
    val probability_of_thunder: Float
)
