package com.example.app_en_desarollo.ui.screen

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.data.preferences.SesionBiometrica
import com.example.app_en_desarollo.model.TipoUsuario
import com.example.app_en_desarollo.ui.components.FooterAdmin
import com.example.app_en_desarollo.ui.viewmodel.PerfilViewModel
import com.example.app_en_desarollo.ui.viewmodel.TrabajadorViewModel


@Composable
fun ScreenTrabajador(
    onNavigateToLogin: () -> Unit,
    onNavigateToMenu: () -> Unit,
    onNavigateToCatalogo: () -> Unit,
    onNavigateToReserva: () -> Unit,
    viewModel: TrabajadorViewModel = viewModel()
) {
    val trabajadores = viewModel.trabajadores
    val seleccionado = viewModel.trabajadorSeleccionado

    var mostrarConfirmacion by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit){
        viewModel.listarTrabajadores()
    }

    Scaffold(
        topBar = { Header(title = "PERSONAL") },
        bottomBar = {
            FooterAdmin(
                onCalendarioClick = {

                },

                onProductosClick = {

                },

                onReservasClick = {

                },

                onCostoClick = {
                },

                onPersonalClick = {

                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFC5D3)),
            contentAlignment = Alignment.TopCenter
        ) {Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "Personal",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {

                items(trabajadores) { trabajador ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable() {
                                viewModel.seleccionarTrabajador(trabajador)
                            }
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = trabajador.nombreCompleto
                            )

                            Text(
                                text = if (trabajador.estado) {
                                    "No disponible"
                                } else {
                                    "Disponible"
                                },
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            seleccionado?.let { trabajador ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                        Text(
                            text = "Información del trabajador",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Nombre: ${trabajador.nombreCompleto}")
                        Text("RUT: ${trabajador.rut}")
                        Text("Correo: ${trabajador.email}")
                        Text("Dirección: ${trabajador.direccion}")

                        Text(
                            text = if (trabajador.estado) {
                                "Estado: No disponible"
                            } else {
                                "Estado: Disponible"
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            // después conectaremos la lista de eventos
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Asignar evento")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            mostrarConfirmacion = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Eliminar trabajador")
                    }
                }
            }
        }
            if (mostrarConfirmacion && seleccionado != null) {

                AlertDialog(
                    onDismissRequest = {
                        mostrarConfirmacion = false
                    },

                    title = {
                        Text("Eliminar trabajador")
                    },

                    text = {
                        Text(
                            "¿Está seguro de eliminar a ${seleccionado.nombreCompleto}?"
                        )
                    },

                    confirmButton = {

                        Button(
                            onClick = {

                                seleccionado.id?.let { id ->
                                    viewModel.borrarTrabajador(id)
                                }

                                viewModel.limpiarTrabajadorSeleccionado()

                                mostrarConfirmacion = false
                            }
                        ) {
                            Text("Sí, eliminar")
                        }
                    },

                    dismissButton = {

                        Button(
                            onClick = {
                                mostrarConfirmacion = false
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ScreenTrabajadorPreview() {
    ScreenTrabajador(
        onNavigateToLogin = {},
        onNavigateToMenu = {},
        onNavigateToCatalogo = {},
        onNavigateToReserva = {}
    )
}