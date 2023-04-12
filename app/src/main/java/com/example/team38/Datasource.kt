import com.example.team38.ForecastData
import com.example.team38.StromprisData
import com.example.team38.StromprisTimer
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.engine.okhttp.*

class Datasource(val pathStrom: String, val pathForecast: String, val pathFrost: String){
    private val client = HttpClient(OkHttp){
        install(ContentNegotiation){
            json()
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

    suspend fun fetchForecastData(): List<Float> {
        val response = client.get(pathForecast) {
            headers {
                append("X-Gravitee-API-Key", apiKey)
            }
        }
        val jsonBody: List<ForecastData> = response.body()
        return emptyList()
    }

}