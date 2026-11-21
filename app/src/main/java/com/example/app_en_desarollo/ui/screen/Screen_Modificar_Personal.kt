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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
fun ModificarTrabajadorScreen(
    onNavigateBack: () -> Unit,
    viewModel: TrabajadorViewModel
) {

    val trabajadores = viewModel.trabajadores
    val trabajadorSeleccionado = viewModel.trabajadorSeleccionado
    val state = viewModel.uiState
    val context = LocalContext.current

    var menuAbierto by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.limpiarTrabajadorSeleccionado()
        viewModel.listarTrabajadores()
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
                onCostoClick = {},
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
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = "Modificar Trabajador",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box {

                OutlinedButton(
                    onClick = {
                        menuAbierto = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                ) {

                    Text(
                        text = viewModel.trabajadorSeleccionado
                            ?.nombreCompleto
                            ?: "Seleccionar trabajador"
                    )
                }

                DropdownMenu(
                    expanded = menuAbierto,
                    onDismissRequest = {
                        menuAbierto = false
                    }
                ) {

                    trabajadores.forEach { trabajador ->

                        DropdownMenuItem(
                            text = {
                                Text(trabajador.nombreCompleto)
                            },

                            onClick = {
                                viewModel.seleccionarTrabajador(trabajador)
                                menuAbierto = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {

                    TextField(
                        value = state.nombreCompleto,
                        onValueChange = {
                            viewModel.onNombreChanged(it)
                        },
                        label = {
                            Text(
                                text = "Nombre",
                                fontSize = 16.sp
                            )
                        },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedLabelColor = Color.DarkGray,
                            unfocusedLabelColor = Color.DarkGray
                        )
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    TextField(
                        value = state.rut,
                        onValueChange = {
                            viewModel.onRutChanged(it)
                        },
                        label = {
                            Text(
                                text = "RUT",
                                fontSize = 16.sp
                            )
                        },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedLabelColor = Color.DarkGray,
                            unfocusedLabelColor = Color.DarkGray
                        )
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    TextField(
                        value = state.correo,
                        onValueChange = {
                            viewModel.onEmailChanged(it)
                        },
                        label = {
                            Text(
                                text = "Correo",
                                fontSize = 16.sp
                            )
                        },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedLabelColor = Color.DarkGray,
                            unfocusedLabelColor = Color.DarkGray
                        )
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    TextField(
                        value = state.direccion,
                        onValueChange = {
                            viewModel.onDireccionChanged(it)
                        },
                        label = {
                            Text(
                                text = "Dirección",
                                fontSize = 16.sp
                            )
                        },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedLabelColor = Color.DarkGray,
                            unfocusedLabelColor = Color.DarkGray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    viewModel.trabajadorSeleccionado
                        ?.id
                        ?.let { id ->

                            viewModel.actualizarTrabajador(id = id,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Trabajador modificado correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    onNavigateBack()
                                },

                                onError = {

                                    Toast.makeText(
                                        context,
                                        "Error al modificar el trabajador",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .shadow(8.dp, shape = CircleShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                border = BorderStroke(2.dp, Color.White),
                shape = CircleShape
            ) {
                Text("Modificar Trabajador",
                    fontSize = 17.sp)
            }

            if (trabajadorSeleccionado?.idReservaAsignada != null) {

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Button(
                    onClick = {

                        trabajadorSeleccionado.id?.let { id ->

                            viewModel.cancelarAsignacion(id,
                                onSuccess = {
                                    Toast.makeText(
                                        context,
                                        "Asignación cancelada correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    onNavigateBack()
                                },

                                onError = {

                                    Toast.makeText(
                                        context,
                                        "Error al cancelar la asignación",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = CircleShape
                ) {

                    Text(
                        text = "Cancelar asignación",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}