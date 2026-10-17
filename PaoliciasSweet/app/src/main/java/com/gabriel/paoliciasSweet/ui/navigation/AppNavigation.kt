/*
package com.gabriel.paoliciasSweet.ui.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myappnavigation.ui.screen.Login
import com.example.myappnavigation.ui.screen.Register
import com.example.myappnavigation.ui.screen.ScreenHeroe
import com.gabriel.paoliciasSweet.ui.screen.CategoriaScreen
import java.security.Principal

object Routes{
    const val CATEGORIA = "categoria"
    const val PRODUCTO = "producto/{nombre}"
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController=navController,
        startDestination = Routes.CATEGORIA     //definimos el inicio
    ){
        composable (Routes.CATEGORIA) {
            CategoriaScreen(
                onNavigateToLogin = {
                    navController.navigate()
                }
            )
        }//composable inicial

        composable(Routes.LOGIN) {
            Login(
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                },
                onLoginSuccess = {
                    //navega a la pantalla inicial
                    navController.navigate(Routes.PRINCIPAL) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable (Routes.REGISTER){
            Register(
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        // limpia la pila para no acumular pantallas login activas
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.PRINCIPAL) {
            com.example.myappnavigation.ui.screen.Principal()
        }
    }
}
 */