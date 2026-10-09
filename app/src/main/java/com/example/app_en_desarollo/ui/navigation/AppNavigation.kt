package com.example.app_en_desarollo.ui.navigation


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_en_desarollo.ui.screen.FormularioCliente
import com.example.app_en_desarollo.ui.screen.FormularioSesionCliente
import com.example.app_en_desarollo.ui.screen.PantallaInicialScreen

object Routes{
    const val INICIO = "inicio"
    const val LOGIN = "login"
    const val REGISTER = "register"

    const val MENU = "menu"
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.INICIO, //Pantalla de inicio
        modifier = Modifier.fillMaxSize()
    ){
        composable(Routes.INICIO){
            PantallaInicialScreen(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN)
                }
            )
        }

        composable(Routes.REGISTER){
            FormularioCliente(
                onNavigateToSesion = {
                    navController.navigate(Routes.MENU)
                }
            )
        }

        composable(Routes.LOGIN){
            FormularioSesionCliente (
                onNavigateToMenu = {
                    navController.navigate(Routes.MENU)
                },
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }
    }
}


