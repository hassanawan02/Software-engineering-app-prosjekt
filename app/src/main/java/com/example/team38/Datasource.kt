import com.example.team38.StromprisData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.serialization.gson.*

class Datasource(val path: String){
    private val client = HttpClient(){
        install(ContentNegotiation){
            gson()
        }
    }
    val apiKey = "55a03d0d-b011-4477-9225-f553640c8e3f"
    suspend fun fetchStromprisData(): StromprisData{
        val response = client.get(path) {
            headers {
                append("X-Gravitee-API-Key", apiKey)
            }
        }
        val jsonBody: StromprisData = response.body()
        return jsonBody
    }

}