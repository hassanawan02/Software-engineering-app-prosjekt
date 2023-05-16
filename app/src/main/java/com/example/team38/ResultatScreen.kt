package com.example.team38

import StromprisViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultatScreen(stromprisViewModel: StromprisViewModel = StromprisViewModel(), onNavigateToInstillinger: () -> Unit, onNavigateToStrompris: () -> Unit){
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val icons = listOf(Icons.Default.Home, Icons.Default.Settings, Icons.Default.Search)
    val items = listOf("Stompriser", "Instillinger", "Resultat")
    val selectedItem = remember { mutableStateOf(icons[2]) }
    val itemsWithIcons = icons.zip(items)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet{
                Spacer(Modifier.height(12.dp))
                itemsWithIcons.forEach{(icon, label)->
                    NavigationDrawerItem(
                        icon = { Icon(icon, contentDescription = "Velg skjerm") },
                        label = { Text(label) },
                        selected = icon == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close()}
                            selectedItem.value = icon
                            when(icon) {
                                Icons.Default.Settings -> onNavigateToInstillinger()
                                Icons.Default.Home -> onNavigateToStrompris()
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
                title = { Text(text = "Resultat", color = Color.Black) },

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
                modifier = Modifier.padding(50.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){


                Text("Temperatur fra de to forrige ukene", color = Color.Black)
                VisGraf(viewModel = stromprisViewModel)
            }

        }
    )
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VisGraf(viewModel: StromprisViewModel){
    /* val frostUiState by viewModel.frostUiState.collectAsState()
    val startDate = LocalDate.now().minusWeeks(2)
    val endDate = LocalDate.now()
    val dateRange = (0 until frostUiState.frost.size).map { index ->
        val date = startDate.plusDays(index.toLong())
        date.format(DateTimeFormatter.ofPattern("MMM d"))
    }

    val temperatureRange = (0 until frostUiState.frost.size).map { index ->
        val temperature = frostUiState.frost[index]
        temperature.toString()
    }
    if(frostUiState.frost.isNotEmpty()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .height(200.dp)
        ) {
            val data = frostUiState.frost
            val maxY = data.maxOrNull() ?: 0f
            val maxX = data.size - 1

            val path = Path()
            path.moveTo(0f, size.height - data.first() / maxY * size.height)

            for (i in 1 until data.size) {
                val x = i.toFloat() / maxX * size.width
                val y = size.height - data[i] / maxY * size.height
                path.lineTo(x, y)
            }

            drawPath(
                path = path,
                color = Color.Blue,
                style = Stroke(width = 2f)
            )
        }
    }

     */
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val frostUiState by viewModel.frostUiState.collectAsState()

        //Tar tid før frost data laster inn så får error hvis man ikke tar if-sjekken for tom liste
        if (frostUiState.frost.isNotEmpty()) {
            val data = frostUiState.frost
            val maxY = data.maxOrNull() ?: 0f
            val maxX = data.size - 1

            val startDate = LocalDate.now().minusWeeks(2)
            val endDate = LocalDate.now()

            val dateRange = (data.indices).map { index ->
                val date = startDate.plusDays(index.toLong())
                date.format(DateTimeFormatter.ofPattern("MMM d"))
            }

            val temperatureRange = (data.indices).map { index ->
                val temperature = data[index]
                temperature.toString()
            }

            Canvas(
                modifier = Modifier
                    .height(200.dp)
                    .width(300.dp)
                    .border(border = BorderStroke(3.dp, Color.Black))
            ) {
                val xInterval = size.width / maxX
                val yInterval = size.height / maxY

                val path = Path()
                path.moveTo(0f, size.height - data.first() * yInterval)

                for (i in 1 until data.size) {
                    val x = i.toFloat() * xInterval
                    val y = size.height - data[i] * yInterval
                    path.lineTo(x, y)
                }

                drawPath(
                    path = path,
                    color = Color.Black,
                    style = Stroke(width = 2f)
                )

                val textPaint = androidx.compose.ui.graphics.Paint().asFrameworkPaint()
                textPaint.textSize = 12.sp.toPx()
                textPaint.color = android.graphics.Color.BLACK
                textPaint.isAntiAlias = true
                textPaint.typeface = android.graphics.Typeface.DEFAULT
                textPaint.textAlign = android.graphics.Paint.Align.CENTER

                val stepSize = (data.size / 5).coerceAtLeast(1)
                for (i in data.indices step stepSize) {
                    val x = i.toFloat() * xInterval
                    val y = size.height + 24.dp.toPx()

                    drawContext.canvas.nativeCanvas.drawText(
                        dateRange[i],
                        x,
                        y,
                        textPaint
                    )
                }

                textPaint.textAlign = android.graphics.Paint.Align.RIGHT
                textPaint.typeface = android.graphics.Typeface.DEFAULT_BOLD

                for (i in data.indices step stepSize) {
                    val x = -8.dp.toPx()
                    val y = size.height - i.toFloat() * (size.height / 5f)

                    drawContext.canvas.nativeCanvas.drawText(
                        temperatureRange[i],
                        x,
                        y,
                        textPaint
                    )
                }

                // Draw the current date at the end of the graph
                val currentDate = endDate.format(DateTimeFormatter.ofPattern("MMM d"))
                val currentX = maxX.toFloat() * xInterval
                val currentY = size.height + 24.dp.toPx()

                drawContext.canvas.nativeCanvas.drawText(
                    currentDate,
                    currentX,
                    currentY,
                    textPaint
                )

                // Draw the date two weeks beforehand at the start of the graph
                val startDateText = startDate.format(DateTimeFormatter.ofPattern("MMM d"))
                val startX = 0f
                val startY = size.height + 24.dp.toPx()

                drawContext.canvas.nativeCanvas.drawText(
                    startDateText,
                    startX,
                    startY,
                    textPaint
                )
            }
        }
    }
}
