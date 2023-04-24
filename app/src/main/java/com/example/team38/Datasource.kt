import com.example.team38.FrostData
import com.example.team38.StromprisData
import com.example.team38.StromprisTimer
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.engine.okhttp.*
import kotlinx.serialization.json.Json

class Datasource(val pathStrom: String, val pathForecast: String, val pathFrost: String){
    private val client = HttpClient(OkHttp){
        install(ContentNegotiation){
            json(Json{ignoreUnknownKeys = true})
        }
    }
    val apiKey = "55a03d0d-b011-4477-9225-f553640c8e3f"
    suspend fun fetchStromprisData(): List<StromprisData>{
        val response = client.get(pathStrom) {
            headers {
                append("X-Gravitee-API-Key", apiKey)
            }
        }
        val jsonBody: List<StromprisData> = response.body()
        return jsonBody
    }

    //egen datasource per api?
    suspend fun fetchForecastData(): List<Float> {
        val response = client.get(pathForecast) {
            headers {
                append("X-Gravitee-API-Key", apiKey)
            }
        }
        val jsonBody: ForecastData = response.body()
        var temp: MutableList<Float> = mutableListOf()
        for (time in jsonBody.properties.timeseries) {
            val hour = "${time.time[11]}${time.time[12]}".toInt()
            val check = listOf(0, 6, 12, 18)
            if (hour in check) {
                temp.add(time.data.instant.details.air_temperature)
            }

        }
        return temp
    }

    suspend fun fetchFrostData(): List<Float> {
        val response = client.get(pathFrost) {
            headers {
                append("X-Gravitee-API-Key", apiKey)
            }
        }
        val jsonBody: FrostData = response.body()
        var legacyTemps: MutableList<Float> = mutableListOf()
        for (data in jsonBody.data) {
            for (observation in data.observations) {
                legacyTemps.add(observation.value)
            }
        }
        return legacyTemps
    }

}