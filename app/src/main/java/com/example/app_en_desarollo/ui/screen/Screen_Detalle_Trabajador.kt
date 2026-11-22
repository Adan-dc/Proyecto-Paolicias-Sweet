package com.example.app_en_desarollo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.ui.components.FooterAdmin
import com.example.app_en_desarollo.ui.components.Header
import com.example.app_en_desarollo.ui.viewmodel.TrabajadorViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext

@Composable
fun DetalleTrabajadorScreen(
    onNavigateBack: () -> Unit,
    onNavigateToCosto: () -> Unit,
    viewModel: TrabajadorViewModel
) {

    val trabajador = viewModel.trabajadorSeleccionado
    val reservas = viewModel.reservas
    val reservaSeleccionada = viewModel.reservaSeleccionada
    val tieneEventoAsignado =
        trabajador?.estado == true &&
                trabajador.idReservaAsignada != null
    val eventoAsignado = reservas.find {
        it.id == trabajador?.idReservaAsignada
    }
    val context = LocalContext.current

    var mostrarConfirmacion by remember {
        mutableStateOf(false)
    }

    var menuEventosAbierto by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.listarReservas()
        viewModel.limpiarReservaSeleccionada()
    }

    if (trabajador == null) {
        Text("No se encontró el trabajador")
        return
    }

    Scaffold(
        topBar = {
            Header(title = "DETALLE PERSONAL")
        },
        bottomBar = {
            FooterAdmin(
                onCalendarioClick = {},
                onProductosClick = {},
                onReservasClick = {},
                onCostoClick = {
                    onNavigateToCosto()
                },
                onPersonalClick = {
                    onNavigateBack()
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFC5D3))
                .padding(20.dp, vertical = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Información del trabajador",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 50.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {

                    Text("Nombre: ${trabajador.nombreCompleto}",
                        fontSize = 20.sp
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                    Text("RUT: ${trabajador.rut}",
                        fontSize = 20.sp
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                    Text("Correo: ${trabajador.correo}",
                        fontSize = 20.sp
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                    Text("Dirección: ${trabajador.direccion}",
                        fontSize = 20.sp
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                    Text(
                        text = if (trabajador.estado) {
                            "Estado: No disponible"
                        } else {
                            "Estado: Disponible"
                        },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            if (tieneEventoAsignado) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                        Text(
                            text = "Evento asignado",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        if (eventoAsignado != null) {

                            Text(
                                text = "${eventoAsignado.tipoEvento} • ${eventoAsignado.fecha}",
                                fontSize = 18.sp
                            )

                        } else {

                            Text(
                                text = "No se pudo obtener la información del evento",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (tieneEventoAsignado) {

                Text(
                    text = if (eventoAsignado != null) {
                        "Evento asignado: ${eventoAsignado.tipoEvento} • ${eventoAsignado.fecha}"
                    } else {
                        "El trabajador ya tiene un evento asignado"
                    },
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
            ) {

                OutlinedButton(
                    onClick = {
                        menuEventosAbierto = true
                    },
                    enabled = !tieneEventoAsignado,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.Gray
                    ),
                    shape = CircleShape
                ) {

                    Text(
                        text = if (tieneEventoAsignado) {

                            "Trabajador no disponible"

                        } else if (reservaSeleccionada != null) {

                            "${reservaSeleccionada.tipoEvento} • ${reservaSeleccionada.fecha}"

                        } else {

                            "Seleccionar evento"
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
                                    text = "${reserva.tipoEvento} • ${reserva.fecha}"
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

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {

                    trabajador?.id?.let { idTrabajador ->

                        reservaSeleccionada?.id?.let { idReserva ->

                            viewModel.asignarReserva(
                                idTrabajador,
                                idReserva,

                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Evento asignado correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    onNavigateBack()
                                },

                                onError = {

                                    Toast.makeText(
                                        context,
                                        "Error al asignar el evento",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }
                    }
                },

                enabled =
                    reservaSeleccionada != null &&
                            !tieneEventoAsignado,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .shadow(8.dp, shape = CircleShape),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.LightGray,
                    disabledContentColor = Color.Gray
                ),

                border = BorderStroke(
                    2.dp,
                    Color.White
                ),

                shape = CircleShape
            ) {

                Text("Asignar Evento")
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {
                    mostrarConfirmacion = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .shadow(8.dp, shape = CircleShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black),
                border = BorderStroke(2.dp, Color.White),
                shape = CircleShape
            ) {
                Text("Eliminar Trabajador",
                    fontSize = 17.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
    if (mostrarConfirmacion) {

        AlertDialog(
            onDismissRequest = {
                mostrarConfirmacion = false
            },

            title = {
                Text("Eliminar trabajador")
            },

            text = {
                Text("¿Está seguro de eliminar este trabajador?")
            },

            confirmButton = {
                Button(
                    onClick = {

                        trabajador?.id?.let { id ->
                            viewModel.borrarTrabajador(id,
                                onSuccess = {

                                    mostrarConfirmacion = false

                                    Toast.makeText(
                                        context,
                                        "Trabajador eliminado correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    onNavigateBack()
                                },

                                onError = {

                                    mostrarConfirmacion = false

                                    Toast.makeText(
                                        context,
                                        "Error al eliminar el trabajador",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .shadow(8.dp, shape = CircleShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black),
                    border = BorderStroke(2.dp, Color.White),
                    shape = CircleShape
                ) {
                    Text("Sí, eliminar")
                }
            },

            dismissButton = {
                Button(
                    onClick = {
                        mostrarConfirmacion = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .shadow(8.dp, shape = CircleShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black),
                    border = BorderStroke(2.dp, Color.White),
                    shape = CircleShape
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

