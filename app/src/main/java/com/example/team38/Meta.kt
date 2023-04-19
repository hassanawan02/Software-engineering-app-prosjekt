package com.example.team38

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("units") val units : Units
)
