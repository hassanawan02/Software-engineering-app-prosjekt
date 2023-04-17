package com.example.team38
import kotlinx.serialization.Serializable

@Serializable
data class Details6Hours(
    val air_temperature_max: Float,
    val air_temperature_min: Float,
    val precipitation_amount: Float,
    val precipitation_amount_max: Float,
    val precipitation_amount_min: Float,
    val probability_of_precipitation: Float
)
