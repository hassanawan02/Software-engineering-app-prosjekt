package com.example.team38

import kotlinx.serialization.Serializable

@Serializable
data class FrostData (
    val data: List<FrostInfo>
)