import com.example.team38.Geometry
import com.example.team38.Properties
import com.example.team38.Time

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ForecastData(
    val type: String,
    val geometry: Geometry,
    val properties: Properties
)