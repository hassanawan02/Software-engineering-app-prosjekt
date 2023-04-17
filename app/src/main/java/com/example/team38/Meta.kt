package com.example.team38

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    @SerializedName("updated_at") var updated_at : String,
    @SerializedName("units") var units : List<Units>
)
