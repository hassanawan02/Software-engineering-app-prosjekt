package com.example.team38

import kotlinx.serialization.Serializable

@Serializable
data class StromprisTimer(
    val timer: List<StromprisData>
) {
}