package com.example.team38

import kotlinx.serialization.Serializable

@Serializable
data class Data (
    val instant: Instant,
    val next_12_hours: TwelveHours,
    val next_1_hours: OneHours,
    val next_6_hours: SixHours
)