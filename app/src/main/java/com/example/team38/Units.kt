package com.example.team38

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Units(
    @SerializedName("air_pressure_at_sea_level") val air_pressure_at_sea_level : String,
    @SerializedName("air_temperature") val air_temperature : String,
    @SerializedName("air_temperature_max") val air_temperature_max : String,
    @SerializedName("air_temperature_min") val air_temperature_min : String,
    @SerializedName("cloud_area_fraction") val cloud_area_fraction : String,
    @SerializedName("cloud_area_fraction_high") val cloud_area_fraction_high : String,
    @SerializedName("cloud_area_fraction_low") val cloud_area_fraction_low : String,
    @SerializedName("cloud_area_fraction_medium") val cloud_area_fraction_medium : String,
    @SerializedName("dew_point_temperature") val dew_point_temperature : String,
    @SerializedName("fog_area_fraction") val fog_area_fraction : String,
    @SerializedName("precipitation_amount") val precipitation_amount : String,
    @SerializedName("precipitation_amount_max") val precipitation_amount_max : String,
    @SerializedName("precipitation_amount_min") val precipitation_amount_min : String,
    @SerializedName("probability_of_precipitation") val probability_of_precipitation : String,
    @SerializedName("probability_of_thunder") val probability_of_thunder : String,
    @SerializedName("relative_humidity") val relative_humidity : String,
    @SerializedName("ultraviolet_index_clear_sky") val ultraviolet_index_clear_sky : String,
    @SerializedName("wind_from_direction") val wind_from_direction : String,
    @SerializedName("wind_speed") val wind_speed : String,
    @SerializedName("wind_speed_of_gust") val wind_speed_of_gust : String
)
