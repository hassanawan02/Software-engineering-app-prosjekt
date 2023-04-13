package com.example.team38

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Units(
    @SerializedName("air_pressure_at_sea_level") var air_pressure_at_sea_level : String,
    @SerializedName("air_temperature") var air_temperature : String,
    @SerializedName("air_temperature_max") var air_temperature_max : String,
    @SerializedName("air_temperature_min") var air_temperature_min : String,
    @SerializedName("cloud_area_fraction") var cloud_area_fraction : String,
    @SerializedName("cloud_area_fraction_high") var cloud_area_fraction_high : String,
    @SerializedName("cloud_area_fraction_low") var cloud_area_fraction_low : String,
    @SerializedName("cloud_area_fraction_medium") var cloud_area_fraction_medium : String,
    @SerializedName("dew_point_temperature") var dew_point_temperature : String,
    @SerializedName("fog_area_fraction") var fog_area_fraction : String,
    @SerializedName("precipitation_amount") var precipitation_amount : String,
    @SerializedName("precipitation_amount_max") var precipitation_amount_max : String,
    @SerializedName("precipitation_amount_min") var precipitation_amount_min : String,
    @SerializedName("probability_of_precipitation") var probability_of_precipitation : String,
    @SerializedName("probability_of_thunder") var probability_of_thunder : String,
    @SerializedName("relative_humidity") var relative_humidity : String,
    @SerializedName("ultraviolet_index_clear_sky") var ultraviolet_index_clear_sky : String,
    @SerializedName("wind_from_direction") var wind_from_direction : String,
    @SerializedName("wind_speed") var wind_speed : String,
    @SerializedName("wind_speed_of_gust") var wind_speed_of_gust : String
)
