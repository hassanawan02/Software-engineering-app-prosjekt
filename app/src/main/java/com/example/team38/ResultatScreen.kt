package com.example.team38

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultatScreen(onNavigateToInstillinger: () -> Unit, onNavigateToStrompris: () -> Unit){
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
                        icon = { Icon(icon, contentDescription = null) },
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
                title = { Text(text = "Resultat") },

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
                modifier = Modifier.background(color = Color.White)
            ){


                Text("hei dette er resultat skjermen", color = Color.Black)
            }

        }
    )
}
