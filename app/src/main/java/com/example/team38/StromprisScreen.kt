package com.example.team38

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StromprisScreen(stromprisViewModel: StromprisViewModel = StromprisViewModel(), onNavigateToInstillinger: () -> (Unit), onNavigateToResultat: () -> (Unit)){

    val stromprisUiState by stromprisViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val icons = listOf(Icons.Default.Home, Icons.Default.Settings, Icons.Default.Search)
    val items = listOf("Stompriser", "Instillinger", "Resultat")
    val selectedItem = remember {mutableStateOf(icons[0])}
    val itemsWithIcons = icons.zip(items)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet{
                Spacer(Modifier.height(12.dp))
                itemsWithIcons.forEach{(icon, label)->
                    NavigationDrawerItem(
                        icon = {Icon(icon, contentDescription = null)},
                        label = {Text(label)},
                        selected = icon == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close()}
                            selectedItem.value = icon
                            when(icon) {
                                Icons.Default.Settings -> onNavigateToInstillinger()
                                Icons.Default.Search -> onNavigateToResultat()
                                else -> {}
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            TopAppBar(
                title = {Text(text = "Strompriser", color = Color.Black)},

                navigationIcon = {
                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .fillMaxSize()
                    ) {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        }
                    }
                },



                )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(50.dp),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                LagKort(stromprisUiState, stromprisViewModel)
            }
        }
    )






}

@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VisData(viewModel: StromprisViewModel){
    val stromprisUiStateNaa by viewModel.uiState.collectAsState()
    val forecastUiState by viewModel.forecastUiState.collectAsState()
    val frostUiState by viewModel.frostUiState.collectAsState()
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(60.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val ordentligTid = LocalDateTime.now().hour

            val faktiskDataNaa = stromprisUiStateNaa.stromPris
            var antallNaa = 0.0
            val tidsListeNaa = emptyList<Double>().toMutableList()
            for(i in faktiskDataNaa){
                antallNaa += i.NOK_per_kWh
                tidsListeNaa.add(i.NOK_per_kWh)
                //Får ut strømprisen for den faktiske timen man er på dagen
                //Bruker sp ved font for universell utforming
                if(tidsListeNaa.indexOf(i.NOK_per_kWh) == ordentligTid){
                    Text("Strømpris i Oslo akkurat nå ${i.NOK_per_kWh} kr/kWh\n", color = Color.Black, style = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 16.sp))
                }

            }
            val gjennomsnitt = antallNaa/24
            //Formatterer gjennomsnittet slik at det ser mer brukervennlig ut
            val formattert = "%.4f".format(gjennomsnitt)
            Text("Gjennomsnitt for dagen: $formattert kr/kWh \n", color = Color.Black, style = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 16.sp))
            Text("Locationforecast: ${forecastUiState.forecast}\n", color = Color.Black,style = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 16.sp))


            Text("Frost: ${frostUiState.frost}", color = Color.Black,  style = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 16.sp))
        }
    }

}






@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LagKort(stromprisUiState: StromprisUiState, viewModel: StromprisViewModel){
    val data = stromprisUiState.stromPris[0]
    val liste = listOf(data)

    LazyColumn{
        items(liste){

            VisData(viewModel = viewModel)
        }
    }


}



