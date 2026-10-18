package com.matias.paoliciassweet.Navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.matias.paoliciassweet.Screen.ObservacionesScreen
import com.matias.paoliciassweet.Screen.ReservaScreen
import com.matias.paoliciassweet.ui.viewmodel.ReservaViewModel

object Routes{
    const val RESERVA = "reserva"
    const val OBSERVACIONES = "observaciones"
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    val reservaViewModel: ReservaViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.RESERVA,
        modifier = Modifier.fillMaxSize()
    ){
        composable(Routes.RESERVA){
            ReservaScreen(
                onNavigateToObservaciones = {
                    navController.navigate(Routes.OBSERVACIONES)
                },
                viewModel = reservaViewModel
            )
        }

        composable(Routes.OBSERVACIONES) {
            ObservacionesScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                viewModel = reservaViewModel
            )
        }
    }
}