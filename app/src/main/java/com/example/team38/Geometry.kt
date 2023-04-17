package com.example.team38

import kotlinx.serialization.Serializable

@Serializable
data class Geometry (
    val type: String,
    val coordinates: List<Float>
)