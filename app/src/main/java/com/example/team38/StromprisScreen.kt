package com.example.team38

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
fun StromprisScreen(stromPrisViewModel: StromprisViewModel = StromprisViewModel(), onNavigateToInstillinger: () -> (Unit), onNavigateToResultat: () -> (Unit), onNavigateToOmOss: () -> (Unit), onNavigateToHome: () -> Unit){

    //Kilde https://snl.no/Oslos_geologi_og_landformer
    val imageOslo = painterResource(id = R.drawable.oslo)
    //Kilde https://snl.no/Bergen
    val imageBergen = painterResource(id = R.drawable.bergen)
    //Kilde https://commons.wikimedia.org/wiki/File:Stavanger_sett_fra_fly.jpg
    val imageStavanger = painterResource(id = R.drawable.stavanger)
    //Kilde https://snl.no/Trondheim
    val imageTrondheim = painterResource(id = R.drawable.trondheim)
    val forecastUiState by stromPrisViewModel.forecastUiState.collectAsState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val icons = listOf(Icons.Default.Home, Icons.Default.LocationOn, Icons.Default.Settings, Icons.Default.Search, Icons.Default.Info)
    val items = listOf("Home", "Stømpriser", "Instillinger", "Resultat", "Om oss")
    val selectedItem = remember {mutableStateOf(icons[1])}
    val itemsWithIcons = icons.zip(items)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet{
                Spacer(Modifier.height(12.dp))
                itemsWithIcons.forEach{(icon, label)->
                    NavigationDrawerItem(
                        icon = {Icon(icon, contentDescription = "hamburger meny")},
                        label = {Text(label)},
                        selected = icon == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close()}
                            selectedItem.value = icon
                            when(icon) {
                                Icons.Default.Home -> onNavigateToHome()
                                Icons.Default.Settings -> onNavigateToInstillinger()
                                Icons.Default.Search -> onNavigateToResultat()
                                Icons.Default.Info -> onNavigateToOmOss()
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
                    .fillMaxWidth()
                    .padding(50.dp),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                LazyColumn {
                    item {
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = Color.White),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            )
                            {
                                Text("Oslo \n", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                Box(modifier = Modifier.height(125.dp)) {
                                    Image(
                                        painter = imageOslo,
                                        //Bildebeskrivelse for universell utforming
                                        contentDescription = "Bilde av Oslo",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                //sjekker om den er tom fordi det tar tid før den laster inn, tar index 0 for været akkurat denne timen
                                if(forecastUiState.forecast.isNotEmpty()){
                                    Text("${forecastUiState.forecast[0]} C", modifier = Modifier.align(Alignment.Start))
                                }

                                Button(
                                    onClick = onNavigateToResultat,
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(4.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB042FF))
                                ) {
                                    Text("Sjekk resultatene", color = Color.White)
                                }
                            }

                        }
                    }
                    item{
                        ElevatedCard(modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = Color.White),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center)
                            {
                                Text("Bergen \n", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                Box(modifier = Modifier.height(125.dp)) {
                                    Image(
                                        painter = imageBergen,
                                        //Bildebeskrivelse for universell utforming
                                        contentDescription = "Bilde av Bergen",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Button(onClick = { }, modifier= Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB042FF)) ) {
                                    Text("Sjekk resultatene", color = Color.White)
                                }
                            }

                        }
                    }
                    item{
                        ElevatedCard(modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = Color.White),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center)
                            {
                                Text("Stavanger \n", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                Box(modifier = Modifier.height(125.dp)) {
                                    Image(
                                        painter = imageStavanger,
                                        //Bildebeskrivelse for universell utforming
                                        contentDescription = "Bilde av Stavanger",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Button(onClick = { }, modifier= Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB042FF)) ) {
                                    Text("Sjekk resultatene", color = Color.White)
                                }
                            }

                        }
                    }
                    item{
                        ElevatedCard(modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = Color.White),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center)
                            {
                                Text("Trondheim \n", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                Box(modifier = Modifier.height(125.dp)) {
                                    Image(
                                        painter = imageTrondheim,
                                        //Bildebeskrivelse for universell utforming
                                        contentDescription = "Bilde av Trondheim",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Button(onClick = { }, modifier= Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB042FF)) ) {
                                    Text("Sjekk resultatene", color = Color.White)
                                }
                            }

                        }
                    }
                }

            }
        }
    )






}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VisData(viewModel: StromprisViewModel){
    val stromprisUiStateNaa by viewModel.uiState.collectAsState()
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
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
                    //Hvis prisen er lavere enn 0.85 så er prisen lav, og da vil det være grønn og kort spacer, ellers rødt
                    if(i.NOK_per_kWh >= 0.85){
                        Spacer(modifier = Modifier
                            .height(10.dp)
                            .background(color = Color.Red)
                            .width(100.dp))
                    }else{
                        Spacer(modifier= Modifier
                            .height(10.dp)
                            .background(color = Color.Green)
                            .width(50.dp))
                    }
                }

            }
            val gjennomsnitt = antallNaa/24
            //Formatterer gjennomsnittet slik at det ser mer brukervennlig ut
            val formattert = "%.4f".format(gjennomsnitt)
            Text("Gjennomsnitt for dagen: $formattert kr/kWh \n", color = Color.Black, style = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 16.sp))
        }
    }


}








