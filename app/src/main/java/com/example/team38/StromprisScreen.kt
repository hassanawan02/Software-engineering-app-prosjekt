package com.example.team38

import StromprisViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


//DENNE FÅR ERROR IKKE GLEM DETTE
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StromprisScreen(stromprisViewModel: StromprisViewModel = StromprisViewModel()){

    val stromprisUiState by stromprisViewModel.uiState.collectAsState()

    Column{
        lagKort(stromprisUiState, stromprisViewModel)
    }


}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun visData(stromprisData: StromprisData, viewModel: StromprisViewModel){
    val stromprisUiState by viewModel.uiState.collectAsState()
    val forecastUiState by viewModel.forecastUiState.collectAsState()
    val frostUiState by viewModel.frostUiState.collectAsState()
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(60.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Strømpris: ${stromprisData.NOK_per_kWh} kr/kWh")
            Text("Locationforecast: ${forecastUiState.forecast}")
            Text("Frost: ${frostUiState.frost}")
        }
    }

}
@Composable
fun lagKort(stromprisUiState: StromprisUiState, viewModel: StromprisViewModel){
    val data = stromprisUiState.stromPris[0];
    val liste = listOf(data)
    LazyColumn{
        items(liste){index ->
            visData(stromprisData = index, viewModel = viewModel)
        }
    }
}
