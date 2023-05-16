import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team38.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

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
    var lat = 59.91
    var lon = 10.75
    var prisomraade = "NO1"
    private var dagensDato = Calendar.getInstance()
    private var aaret = dagensDato.get(Calendar.YEAR)
    private var ekteMaaned = dagensDato.get(Calendar.MONTH) + 1
    private var ekteDag = dagensDato.get(Calendar.DAY_OF_MONTH)
    var faktiskMaaned = ekteMaaned.toString()
    var sjekker = if(faktiskMaaned.toInt() < 9 || faktiskMaaned.toInt() == 9){ "0${ekteMaaned}"}else{ekteMaaned.toString()}

    private val baseUrlForecast = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/locationforecast/2.0/complete?lat=$lat&lon=$lon"
    private val baseUrlFrost = "https://gw-uio.intark.uh-it.no/in2000/frostapi/observations/v0.jsonld?sources=SN18700%3Aall&referencetime=$aaret-${ekteMaaned}-${ekteDag-14}%2F$aaret-${ekteMaaned}-$ekteDag&elements=air_temperature"

    private var dataSource = Datasource("$baseUrl/$aaret/${sjekker}-${ekteDag}_$prisomraade.json", baseUrlForecast, baseUrlFrost)
    /*
    init{
        viewModelScope.launch{


            lastInnStrompris()
            lastInnForecast()
            lastInnFrost()
        }
    }

     */
    private fun setDatasource(aar: Int, maaned: Int, dag: Int, prisomraade: String, latitude: Double, longitude: Double) {
        this.lat = latitude
        this.lon = longitude
        dataSource = Datasource("$baseUrl/$aar/$maaned-$dag" + "_$prisomraade.json", baseUrlForecast, baseUrlFrost)
    }


    init{
        viewModelScope.launch{

            lastInnStrompris()
            lastInnForecast()
            lastInnFrost()
        }
    }
    private fun lastInnStrompris(){
        viewModelScope.launch(Dispatchers.IO){
            val stromPriser = dataSource.fetchStromprisData()
            _uiState.value = StromprisUiState(stromPris = stromPriser)
        }
    }
    private fun lastInnStromprisNO2(){
        viewModelScope.launch(Dispatchers.IO){
            val prisomraadeNO2 = "NO2"
            val nyDatasource = Datasource("$baseUrl/$aaret/${sjekker}-${ekteDag}_$prisomraadeNO2.json", baseUrlForecast, baseUrlFrost)
            val stromPriser = nyDatasource.fetchStromprisData()
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