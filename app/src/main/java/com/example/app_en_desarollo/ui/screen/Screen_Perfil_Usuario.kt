package com.example.app_en_desarollo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_en_desarollo.ui.components.Footer
import com.example.app_en_desarollo.ui.components.Header
import com.example.app_en_desarollo.ui.viewmodel.LoginViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.data.preferences.SesionBiometrica
import com.example.app_en_desarollo.ui.viewmodel.PerfilViewModel

@Composable
fun infoPerfil(
    onNavigateToLogin: () -> Unit,
    onNavigateToMenu: () -> Unit,
    onNavigateToCatalogo: () -> Unit,
    onNavigateToReserva: () -> Unit,
    viewModel: PerfilViewModel = viewModel()
) {
    val context = LocalContext.current
    val usuario = viewModel.usuario

    LaunchedEffect(Unit){
        viewModel.cargarUsuario(context)
    }

    Scaffold(
        topBar = { Header(title = "PERFIL") },
        bottomBar = {
            Footer(
                onInicioClick = {
                    onNavigateToMenu()
                },

                onCatalogoClick = {
                    onNavigateToCatalogo()
                },

                onReservaClick = {
                    onNavigateToReserva()
                },

                onPerfilClick = {
                }
            )
        }
    ) {
            innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFC5D3)),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                Text(
                    text = "Datos personales:",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (usuario != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                text = usuario.nombre,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                text = "⛾Empresa: ${usuario.empresa ?: "No registrada"}",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()

                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "✆Teléfono: ${usuario.telefono}",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "✉Correo: ${usuario.email}",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }

                } else {
                    Text(
                        text = "Cargando datos del usuario..."
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        SesionBiometrica.cerrarSesion(context)
                        onNavigateToLogin()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .shadow(8.dp, shape = CircleShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(2.dp, Color.White),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Cerrar Sesión",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    )
                }
            }
        }
    }
}