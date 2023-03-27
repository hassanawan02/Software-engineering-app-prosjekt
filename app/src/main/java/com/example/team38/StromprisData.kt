package com.example.team38

import com.google.gson.annotations.SerializedName

data class StromprisData(
    @SerializedName("NOK_per_kWh") var NOK_per_kWh: Double,
    @SerializedName("EUR_per_kWh") var EUR_per_kWh: Double,
    @SerializedName("EXR") var EXR: Double,
    @SerializedName("time_start") var time_start: String,
    @SerializedName("time_end") var time_end: String
)
