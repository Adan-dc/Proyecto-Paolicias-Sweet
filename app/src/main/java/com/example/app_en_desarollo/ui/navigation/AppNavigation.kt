package com.example.app_en_desarollo.ui.navigation


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_en_desarollo.ui.screen.FormularioRegistroCliente
import com.example.app_en_desarollo.ui.screen.FormularioSesionCliente
import com.example.app_en_desarollo.ui.screen.MenuProductos
import com.example.app_en_desarollo.ui.screen.infoPerfil

object Routes{
    const val REGISTER = "register"

    const val MENU = "menu"

    const val PERFIL = "perfil"

    const val CATALOGO = "catalogo"

    const val RESERVA = "reserva"

    const val LOGIN = "login"
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    fun navegarFooter(ruta: String){
        navController.navigate(ruta){
            popUpTo(Routes.CATALOGO){
                saveState =  true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN, //Pantalla de inicio
        modifier = Modifier.fillMaxSize()
    ){
        composable(Routes.LOGIN){
            FormularioSesionCliente (
                onNavigateToMenu = {
                    navController.navigate(Routes.CATALOGO) {
                        popUpTo(Routes.LOGIN){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateToRegister = {
                    navegarFooter(Routes.REGISTER)
                },
                onNavigateToCatalogo = {
                    navegarFooter(Routes.CATALOGO)
                },
                onNavigateToReserva = {
                    navegarFooter(Routes.RESERVA)
                },
                onNavigateToPerfil = {
                    navegarFooter(Routes.PERFIL)
                }
            )
        }

        composable(Routes.REGISTER){
            FormularioRegistroCliente(
                onNavigateToSesion = {
                    navegarFooter(Routes.LOGIN)
                },
                onNavigateToMenu = {
                    navegarFooter(Routes.MENU)
                },
                onNavigateToCategoria = {
                    navegarFooter(Routes.CATALOGO)
                },
                onNavigateToReserva = {
                    navegarFooter(Routes.RESERVA)
                },
                onNavigateToPerfil = {
                    navegarFooter(Routes.PERFIL)
                }
            )
        }

        composable(Routes.PERFIL){
            infoPerfil(
                onNavigateToMenu = {
                    navegarFooter(Routes.MENU)
                },
                onNavigateToCatalogo = {
                    navegarFooter(Routes.CATALOGO)
                },
                onNavigateToReserva = {
                    navegarFooter(Routes.RESERVA)
                },
                //revisar esto tambien mandar a menu
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN){
                        popUpTo(navController.graph.startDestinationId){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.CATALOGO){
            MenuProductos(
                //aca en vez de login mandar a menu
                onNavigateToInicio = {
                    navegarFooter(Routes.LOGIN)
                },
                onNavigateToReserva = {
                    navegarFooter(Routes.RESERVA)
                },
                onNavigateToPerfil = {
                    navegarFooter(Routes.PERFIL)
                }
            )
        }
    }
}


