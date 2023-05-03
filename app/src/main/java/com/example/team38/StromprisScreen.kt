package com.example.team38

import StromprisViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch


//DENNE FÅR ERROR IKKE GLEM DETTE





@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                title = {Text(text = "Strompriser")},

                navigationIcon = {
                    Box(
                        contentAlignment = Alignment.CenterEnd,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .fillMaxSize()
                    ) {
                        IconButton(onClick = { scope.launch { drawerState.open() } },) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        }
                    }
                },



                )
            Column(
                modifier = Modifier.fillMaxSize().padding(50.dp),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                lagKort(stromprisUiState, stromprisViewModel)
            }
        }
    )






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
            modifier = Modifier.fillMaxWidth().background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Strømpris: ${stromprisData.NOK_per_kWh} kr/kWh", color = Color.Black)
            Text("Locationforecast: ${forecastUiState.forecast}", color = Color.Black)
            Text("Frost: ${frostUiState.frost}", color = Color.Black)
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
