package com.ucne.parcial2

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ucne.parcial2.ui.navigation.DrawerMenu
import com.ucne.parcial2.ui.navigation.ScreenModule
import com.ucne.parcial2.ui.theme.Parcial2Theme
import com.ucne.parcial2.ui.tickets.TicketScreen
import com.ucne.parcial2.ui.tickets.TicketsListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Parcial2Theme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenModule.Start.route
                    ) {
                        composable(ScreenModule.Start.route) {
                            DrawerMenu(navController = navController)
                        }
                        composable(route = ScreenModule.TicketsList.route){
                            TicketsListScreen(navController = navController){ id ->
                                navController.navigate(ScreenModule.Tickets.route + "/${id}")
                            }
                        }
                        composable(
                            route = ScreenModule.Tickets.route + "/{id}",
                            arguments = listOf( navArgument("id") { type = NavType.IntType })
                            ) { capturar -> val ticketId = capturar.arguments?.getInt("id") ?: 0

                            TicketScreen(ticketId = ticketId, navController = navController) {
                                navController.navigate(ScreenModule.TicketsList.route)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StartScreen(name: String = "") {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Parcial2Theme() {
        StartScreen("Android")
    }
}