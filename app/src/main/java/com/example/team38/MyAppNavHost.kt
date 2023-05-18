package com.example.team38

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "strompris"
    ) {
        composable("strompris") {
            StromprisScreen(
                onNavigateToInstillinger = { navController.navigate("instillinger") },
                onNavigateToResultat = {navController.navigate("resultat")}
            )
        }
        composable("instillinger") {
            InstillingerScreen(
                onNavigateToResultat = { navController.navigate("resultat") },
                onNavigateToStrompris = {navController.navigate("strompris")}

            )
        }
        composable("resultat"){
            ResultatScreen(
                onNavigateToStrompris = {navController.navigate("strompris")},
                onNavigateToInstillinger = {navController.navigate("instillinger")}
            )
        }
    }
}
