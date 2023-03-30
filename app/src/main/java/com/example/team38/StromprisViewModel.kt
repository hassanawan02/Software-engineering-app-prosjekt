import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team38.StromprisData
import com.example.team38.StromprisUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StromprisViewModel : ViewModel(){
    private val strompriser = StromprisData(0.0, 0.0, 0.0, "", "" )
    private val _uiState = MutableStateFlow(StromprisUiState(stromPris = listOf(strompriser)))
    val uiState: StateFlow<StromprisUiState> = _uiState.asStateFlow()

    private val baseUrl = "https://www.hvakosterstrommen.no/api/v1/prices"
    private var dataSource = Datasource("$baseUrl/2023/03-27_NO5.json")
    init{
        viewModelScope.launch{

            lastInnStrompris()
        }
    }
    private fun setDatasource(aar: Int, maaned: Int, dag: Int, prisomraade: String) {
        dataSource = Datasource("$baseUrl/$aar/$maaned-$dag" + "_$prisomraade.json")
    }
    private fun lastInnStrompris(){
        viewModelScope.launch(Dispatchers.IO){
            val stromPriser = dataSource.fetchStromprisData()
            _uiState.value = StromprisUiState(stromPris = stromPriser)
        }
    }

}