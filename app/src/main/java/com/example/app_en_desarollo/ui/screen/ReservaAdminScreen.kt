package com.example.app_en_desarollo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.data.api.ReservaBackend
import com.example.app_en_desarollo.ui.components.FooterAdmin
import com.example.app_en_desarollo.data.api.RetrofitGeneral
import java.time.LocalDate

private val RosaFondoAdmin = Color(0xFFFCEAF0)
private val GranateAdmin = Color(0xFFA3375D)
private val TextoAdmin = Color(0xFF631B36)

@Composable
fun ReservasAdminScreen(
    onVerDetalle: (Int) -> Unit = {},
    onNavegar: (String) -> Unit = {}
) {
    var reservas by remember {
        mutableStateOf<List<ReservaBackend>>(emptyList())
    }

    var busqueda by remember { mutableStateOf("") }
    var filtro by remember { mutableStateOf("Todas") }
    var cargando by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var actualizar by remember { mutableIntStateOf(0) }

    LaunchedEffect(actualizar) {
        cargando = true
        error = null

        try {
            reservas = RetrofitGeneral.reservaApi
                .obtenerTodasLasReservas()
        } catch (e: Exception) {
            error = e.message ?: "No se pudo conectar"
        } finally {
            cargando = false
        }
    }

    val hoy = LocalDate.now()

    val reservasFiltradas = reservas
        .filter { reserva ->
            val coincideTexto =
                reserva.usuarioEmail.orEmpty()
                    .contains(busqueda, ignoreCase = true) ||
                        reserva.tipoEvento.orEmpty()
                            .contains(busqueda, ignoreCase = true) ||
                        reserva.id.toString().contains(busqueda)
            val finalizada = runCatching {
                LocalDate.parse(reserva.fecha.orEmpty())
                    .isBefore(hoy)
            }.getOrDefault(false)
            val coincideEstado = when (filtro) {
                "Pendientes" ->
                    reserva.estadoReserva == "PENDIENTE" && !finalizada

                "Confirmadas" ->
                    reserva.estadoReserva == "CONFIRMADA" && !finalizada

                "Canceladas" ->
                    reserva.estadoReserva == "CANCELADA" && !finalizada

                "Finalizadas" -> finalizada

                else -> !finalizada
            }

            coincideTexto && coincideEstado
        }
        .sortedWith(
            compareBy<ReservaBackend> {
                val fecha = runCatching {
                    LocalDate.parse(it.fecha.orEmpty())
                }.getOrNull()
                fecha == null || fecha.isBefore(hoy)
            }.thenBy { it.fecha.orEmpty() }
                .thenBy { it.hora.orEmpty() }
        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RosaFondoAdmin)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Paolicias Sweet APP",
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp,
                color = TextoAdmin
            )
            Text(
                "Panel administrador",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Reservas admin",
                    fontSize = 29.sp,
                    color = TextoAdmin
                )

                Text(
                    "Gestiona y revisa las reservas del sistema.",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )

                Spacer(Modifier.height(18.dp))

                OutlinedTextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Buscar cliente, correo o evento")
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(24.dp)
                )

                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier.horizontalScroll(
                        rememberScrollState()
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        "Todas",
                        "Pendientes",
                        "Confirmadas",
                        "Canceladas",
                        "Finalizadas"
                    ).forEach { opcion ->
                        FilterChip(
                            selected = filtro == opcion,
                            onClick = { filtro = opcion },
                            label = { Text(opcion) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = GranateAdmin,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Ordenado por fecha más próxima",
                        color = TextoAdmin,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )

                    TextButton(onClick = { actualizar++ }) {
                        Text("Actualizar", color = GranateAdmin)
                    }
                }
                TextButton(
                    onClick = {
                        onNavegar("Comprobantes")
                    }
                ) {
                    Text(
                        "Revisar comprobantes de pago",
                        color = GranateAdmin
                    )
                }

            }

            if (cargando) {
                item {
                    Box(
                        Modifier.fillMaxWidth().padding(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = GranateAdmin
                        )
                    }
                }
            } else if (error != null) {
                item {
                    Text(
                        "Error: $error",
                        color = Color.Red
                    )
                    Button(onClick = { actualizar++ }) {
                        Text("Reintentar")
                    }
                }
            } else if (reservasFiltradas.isEmpty()) {
                item {
                    Text("No hay reservas para mostrar.")
                }
            } else {
                items(
                    reservasFiltradas,
                    key = { it.id }
                ) { reserva ->
                    TarjetaReservaAdministracion(
                        reserva = reserva,
                        onDetalle = { onVerDetalle(reserva.id) }
                    )
                }
            }
        }


        FooterAdmin(
            seccionActual = "Reservas",
            onCalendarioClick = { onNavegar("Calendario") },
            onProductosClick = { onNavegar("Productos") },
            onCatalogoClick = { onNavegar("Catálogo") },
            onReservasClick = { onNavegar("Reservas") },
            onPersonalClick = { onNavegar("Personal") },
            onPerfilClick = { onNavegar("Perfil") }
        )

    }
}

@Composable
private fun TarjetaReservaAdministracion(
    reserva: ReservaBackend,
    onDetalle: () -> Unit
) {
    val colorEstado = when (reserva.estadoReserva) {
        "CONFIRMADA" -> Color(0xFFD0EFDC)
        "CANCELADA" -> Color(0xFFFFD5DA)
        else -> Color(0xFFFFE7B9)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFAFB)
        )
    ) {
        Column {
            Column(
                modifier = Modifier.padding(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Reserva #${reserva.id}",
                        color = TextoAdmin,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    Surface(
                        color = colorEstado,
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            reserva.estadoReserva ?: "PENDIENTE",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoAdmin,
                            modifier = Modifier.padding(
                                horizontal = 9.dp,
                                vertical = 6.dp
                            )
                        )
                    }
                }

                Text(
                    reserva.tipoEvento ?: "Evento",
                    fontWeight = FontWeight.Bold,
                    color = TextoAdmin
                )

                Text(
                    reserva.usuarioEmail ?: "Sin correo",
                    fontSize = 12.sp
                )

                Spacer(Modifier.height(8.dp))

                Text("Fecha: ${reserva.fecha.orEmpty()}")
                Text("Hora: ${reserva.hora.orEmpty()}")
                Text("Invitados: ${reserva.asistentes}")
                Text("Presupuesto: $${reserva.presupuestoTotal}")

                Spacer(Modifier.height(10.dp))

                Button(
                    onClick = onDetalle,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GranateAdmin
                    )
                ) {
                    Text("Ver detalle")
                }
            }
        }
    }
}

@Composable
private fun FooterAdministracion(
    onNavegar: (String) -> Unit
) {
    val opciones = listOf(
        "Calendario",
        "Productos",
        "Catálogo",
        "Reservas",
        "Personal",
        "Perfil"
    )

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5EEF7))
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            opciones.forEach { opcion ->
                val seleccionado = opcion == "Reservas"

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavegar(opcion) }
                        .background(
                            if (seleccionado)
                                Color(0xFFF6DDE6)
                            else Color.Transparent,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val icono = when (opcion) {
                        "Calendario" -> "▦"
                        "Productos" -> "▦"
                        "Catálogo" -> "▣"
                        "Reservas" -> "▤"
                        "Personal" -> "♟"
                        else -> "●"
                    }

                    Text(
                        icono,
                        color = if (seleccionado)
                            GranateAdmin else Color.DarkGray,
                        fontSize = 19.sp
                    )

                    Text(
                        opcion,
                        color = if (seleccionado)
                            GranateAdmin else Color.DarkGray,
                        fontSize = 9.sp,
                        maxLines = 1
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1B1B1B))
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "© 2026 PaoliciasSweet. Derechos reservados MAG.",
                color = Color.LightGray,
                fontSize = 10.sp
            )
        }
    }
}
