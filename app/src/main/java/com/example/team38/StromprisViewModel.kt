import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team38.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StromprisViewModel : ViewModel(){
    private val strompriser = StromprisData(0.0, 0.0, 0.0, "", "" )
    private val _uiState = MutableStateFlow(StromprisUiState(stromPris = listOf(strompriser)))
    val uiState: StateFlow<StromprisUiState> = _uiState.asStateFlow()

    private val _forecastUiState = MutableStateFlow(ForecastUiState(emptyList()))
    val forecastUiState: StateFlow<ForecastUiState> = _forecastUiState.asStateFlow()

    private val _frostUiState = MutableStateFlow(FrostUiState(emptyList()))
    val frostUiState: StateFlow<FrostUiState> = _frostUiState.asStateFlow()

    //ENDRE URL TIL PROXY
    private val baseUrl = "https://www.hvakosterstrommen.no/api/v1/prices"
    private var lat = 60.10
    private var lon = 10.0
    private val baseUrlForecast = "https://api.met.no/weatherapi/locationforecast/2.0/complete?lat=$lat&lon=$lon"
    private val baseUrlFrost = "https://frost.met.no/observations/v0.jsonld?sources=SN18700%3Aall&referencetime=2023-04-10%2F2023-04-24&elements=Minimum%20temperature%20(12%20hours)"
    private var dataSource = Datasource("$baseUrl/2023/03-27_NO5.json", baseUrlForecast, baseUrlFrost)
    init{
        viewModelScope.launch{

            lastInnStrompris()
            lastInnForecast()
            lastInnFrost()
        }
    }
    private fun setDatasource(aar: Int, maaned: Int, dag: Int, prisomraade: String, lat: Double, lon: Double) {
        this.lat = lat
        this.lon = lon
        dataSource = Datasource("$baseUrl/$aar/$maaned-$dag" + "_$prisomraade.json", baseUrlForecast, baseUrlFrost)
    }
    private fun lastInnStrompris(){
        viewModelScope.launch(Dispatchers.IO){
            val stromPriser = dataSource.fetchStromprisData()
            _uiState.value = StromprisUiState(stromPris = stromPriser)
        }
    }

    private fun lastInnForecast() {
        viewModelScope.launch(Dispatchers.IO) {
            val forecast = dataSource.fetchForecastData()
            _forecastUiState.value = ForecastUiState(forecast)
        }
    }

    private fun lastInnFrost() {
        viewModelScope.launch(Dispatchers.IO) {
            val frost = dataSource.fetchFrostData()
            _frostUiState.value = FrostUiState(frost)
        }
    }
}