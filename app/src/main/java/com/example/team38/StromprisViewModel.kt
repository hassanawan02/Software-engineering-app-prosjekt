import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team38.StromprisData
import com.example.team38.StromprisUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StromprisViewModel : ViewModel(){
    private val strompriser = StromprisData(0.0, 0.0, 0.0, "", "" )
    private val _uiState = MutableStateFlow(StromprisUiState(stromPris = strompriser))
    val uiState: StateFlow<StromprisUiState> = _uiState.asStateFlow()

    private val dataSource = Datasource("https://www.hvakosterstrommen.no/api/v1/prices/2023/03-27_NO5.json")
    init{
        viewModelScope.launch{

            lastInnStrompris()
        }
    }
    private fun lastInnStrompris(){
        viewModelScope.launch{
            val stromPriser = dataSource.fetchStromprisData()
            _uiState.value = StromprisUiState(stromPris = stromPriser)
        }
    }

}