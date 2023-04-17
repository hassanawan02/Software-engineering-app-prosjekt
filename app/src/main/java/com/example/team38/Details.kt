package com.example.team38
import kotlinx.serialization.Serializable

@Serializable

data class InstantDetails(
    val air_pressure_at_sea_level: Float,
    val air_temperature: Float,
    val cloud_area_fraction: Float,
    val cloud_area_fraction_high: Float,
    val cloud_area_fraction_low: Float,
    val cloud_area_fraction_medium: Float,
    val dew_point_temperature: Float,
    val fog_area_fraction: Float,
    val relative_humidity: Float,
    val ultraviolet_index_clear_sky: Float,
    val wind_from_direction: Float,
    val wind_speed: Float,
    val wind_speed_of_gust: Float
)
