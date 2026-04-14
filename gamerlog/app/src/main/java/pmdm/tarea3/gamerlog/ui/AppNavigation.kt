package pmdm.tarea3.gamerlog.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pmdm.tarea3.gamerlog.ui.view.AddGameScreen
import pmdm.tarea3.gamerlog.ui.view.DetailGameScreen
import pmdm.tarea3.gamerlog.ui.view.GameScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {

        //Pantalla Principal
        composable("home") {
            GameScreen(
                onNavigateToDetail = { gameId ->
                    navController.navigate("detail/$gameId")
                },
                onNavigateToAdd = {
                    navController.navigate("add")
                }
            )
        }

        // Pantalla Añadir
        composable("add") {
            AddGameScreen(
                onBack = { navController.popBackStack() }
            )
        }

        // Pantalla Detalle
        composable(
            route = "detail/{gameId}",
            arguments = listOf(navArgument("gameId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("gameId") ?: 0
            DetailGameScreen(
                gameId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}