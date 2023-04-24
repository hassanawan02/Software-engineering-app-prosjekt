package com.example.team38

import kotlinx.serialization.Serializable

@Serializable
data class FrostInfo (
    val geometry: GeometryFrost,
    val referenceTime: String,
    val observations: List<Observation>
)