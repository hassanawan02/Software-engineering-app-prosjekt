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
    private val baseUrl = "https://hvakosterstrommen.no/api/v1/prices"
    private var lat = 60.10
    private var lon = 10.0
    private val currentYear = 2023
    private val currentMonth = 4
    private val currentDate = 27
    private val baseUrlForecast = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/locationforecast/2.0/complete?lat=$lat&lon=$lon"
    private val baseUrlFrost = "https://gw-uio.intark.uh-it.no/in2000/frostapi/observations/v0.jsonld?sources=SN18700%3Aall&referencetime=$currentYear-04-${currentDate-14}%2F$currentYear-04-$currentDate&elements=air_temperature"

    private var dataSource = Datasource("$baseUrl/$currentYear/03-${currentDate}_NO5.json", baseUrlForecast, baseUrlFrost)
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