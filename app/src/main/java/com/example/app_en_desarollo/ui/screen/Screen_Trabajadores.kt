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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.data.preferences.SesionBiometrica
import com.example.app_en_desarollo.model.TipoUsuario
import com.example.app_en_desarollo.ui.components.FooterAdmin
import com.example.app_en_desarollo.ui.navigation.Routes
import com.example.app_en_desarollo.ui.viewmodel.PerfilViewModel
import com.example.app_en_desarollo.ui.viewmodel.TrabajadorViewModel


@Composable
fun ScreenTrabajador(
    onNavigateToCalendario: () -> Unit,
    onNavigateToProductos: () -> Unit,
    onNavigateToCosto: () -> Unit,
    onNavigateToReservas: () -> Unit,
    onNavigateToDetalleTrabajador: () -> Unit,
    onNavigateToCrearTrabajador: () -> Unit,
    onNavigateToModificarTrabajador: () -> Unit,
    viewModel: TrabajadorViewModel
) {
    val trabajadores = viewModel.trabajadores
    val reservas = viewModel.reservas
    val reservaSeleccionada = viewModel.reservaSeleccionada

    var menuEventosAbierto by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit){
        viewModel.listarTrabajadores()
        viewModel.listarReservas()
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
                    onNavigateToCosto()
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
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Text(
                    text = "Personal",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                ) {

                    items(trabajadores) { trabajador ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clickable {
                                    viewModel.seleccionarTrabajador(trabajador)
                                    onNavigateToDetalleTrabajador()
                                }
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .padding(16.dp),
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

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Eventos",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                val cantidadTrabajadores = if (reservaSeleccionada?.id != null) {

                    trabajadores.count {
                        it.idReservaAsignada == reservaSeleccionada.id
                    }

                } else {
                    0
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Box(
                        modifier = Modifier.weight(2f)
                    ) {

                        OutlinedButton(
                            onClick = {
                                menuEventosAbierto = true
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            ),
                        ) {

                            Text(
                                text = if (reservaSeleccionada != null) {

                                    "${reservaSeleccionada.tipoEvento} • ${reservaSeleccionada.fecha}"

                                } else {

                                    "Evento • Fecha"
                                }
                            )
                        }

                        DropdownMenu(
                            expanded = menuEventosAbierto,
                            onDismissRequest = {
                                menuEventosAbierto = false
                            }
                        ) {

                            reservas.forEach { reserva ->

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "${reserva.tipoEvento} • ${reserva.fecha}"
                                        )
                                    },

                                    onClick = {

                                        viewModel.seleccionarReserva(reserva)

                                        menuEventosAbierto = false
                                    }
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Cantidad",
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = if (reservaSeleccionada == null) {
                                "-"
                            } else {
                                "$cantidadTrabajadores"
                            },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(35.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            onNavigateToCrearTrabajador()
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
                            "Crear Trabajador",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            onNavigateToModificarTrabajador()
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
                            "Modificar Trabajador",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp
                        )
                    }
                }
            }
        }
    }
}
