
package com.example.app_en_desarollo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.data.api.ReservaBackend
import com.example.app_en_desarollo.data.api.ReservaProductoAdmin
import com.example.app_en_desarollo.data.api.ReservaPackAdmin
import com.example.app_en_desarollo.data.api.PagoBackend
import com.example.app_en_desarollo.data.api.ComprobanteBackend
import com.example.app_en_desarollo.data.api.RetrofitGeneral
import com.example.app_en_desarollo.ui.components.FooterAdmin
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

private val FondoDetalle = Color(0xFFFCEAF0)
private val GranateDetalle = Color(0xFFA3375D)
private val TextoDetalle = Color(0xFF631B36)
private val RosaDetalle = Color(0xFFF5DAE3)

private fun dineroDetalle(valor: Int): String {
    val formato = NumberFormat.getIntegerInstance(
        Locale("es", "CL")
    )
    return "$" + formato.format(valor)
}

private fun fechaDetalle(fecha: String?): String {
    val partes = fecha.orEmpty().split("-")
    return if (partes.size == 3) {
        "${partes[2]}/${partes[1]}/${partes[0]}"
    } else {
        fecha.orEmpty()
    }
}

@Composable
fun DetalleReservaAdminScreen(
    reservaId: Int,
    onVolver: () -> Unit,
    onNavegar: (String) -> Unit,
    onVerComprobante: (Long) -> Unit
) {
    var reserva by remember {
        mutableStateOf<ReservaBackend?>(null)
    }

    var productos by remember {
        mutableStateOf<List<ReservaProductoAdmin>>(emptyList())
    }

    var packs by remember {
        mutableStateOf<List<ReservaPackAdmin>>(emptyList())
    }

    var pagos by remember {
        mutableStateOf<List<PagoBackend>>(emptyList())
    }

    var comprobantes by remember {
        mutableStateOf<List<ComprobanteBackend>>(emptyList())
    }

    var cargando by remember { mutableStateOf(true) }
    var procesando by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var actualizar by remember { mutableIntStateOf(0) }
    var accionPendiente by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(reservaId, actualizar) {
        cargando = true
        error = null

        try {
            val id = reservaId.toLong()

            reserva = RetrofitGeneral.reservaApi
                .obtenerReservaPorId(id)

            productos = RetrofitGeneral.reservaApi
                .obtenerProductosReserva(id)

            packs = try {
                RetrofitGeneral.reservaApi.obtenerPacksReserva(id)
            } catch (e: retrofit2.HttpException) {
                if (e.code() != 404) throw e
                error = "No se pudieron cargar los packs de esta reserva (404)."
                emptyList()
            }

            pagos = RetrofitGeneral.pagoApi
                .obtenerPagosPorReserva(id)

            val listaComprobantes =
                mutableListOf<ComprobanteBackend>()

            pagos.forEach { pago ->
                val resultado = RetrofitGeneral.comprobanteApi
                    .obtenerPorPago(pago.id)

                listaComprobantes.addAll(resultado)
            }

            comprobantes = listaComprobantes

        } catch (e: Exception) {
            error = e.message ?: "No se pudo cargar la reserva"
        } finally {
            cargando = false
        }
    }

    val pagoAbono = pagos
        .filter { it.tipoPago == "ABONO" }
        .maxByOrNull { it.id }

    val comprobanteActual = comprobantes
        .filter { it.pagoId == pagoAbono?.id }
        .maxByOrNull { it.id }

    if (accionPendiente != null) {
        AlertDialog(
            onDismissRequest = {
                accionPendiente = null
            },
            title = {
                Text("Confirmar acción")
            },
            text = {
                Text(
                    if (accionPendiente == "CONFIRMAR") {
                        "¿Deseas confirmar esta reserva?"
                    } else {
                        "¿Deseas cancelar esta reserva?"
                    }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val accion = accionPendiente
                        accionPendiente = null
                        procesando = true

                        scope.launch {
                            try {
                                if (accion == "CONFIRMAR") {
                                    RetrofitGeneral.reservaApi
                                        .confirmarReserva(
                                            reservaId.toLong()
                                        )
                                } else {
                                    RetrofitGeneral.reservaApi
                                        .cancelarReserva(
                                            reservaId.toLong()
                                        )
                                }

                                actualizar++
                            } catch (e: Exception) {
                                error = e.message
                            } finally {
                                procesando = false
                            }
                        }
                    }
                ) {
                    Text("Aceptar", color = GranateDetalle)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        accionPendiente = null
                    }
                ) {
                    Text("Volver")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoDetalle)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(12.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = onVolver,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        "← Volver",
                        color = GranateDetalle
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Paolicias Sweet APP",
                        color = TextoDetalle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Panel administrador",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "Detalle de reserva",
                fontSize = 28.sp,
                color = TextoDetalle,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            if (cargando) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = GranateDetalle
                    )
                }
            } else {
                val dato = reserva

                if (dato != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFFAFB)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                "Reserva #${dato.id}",
                                color = TextoDetalle,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            EtiquetaDetalle(
                                dato.estadoReserva ?: "PENDIENTE"
                            )

                            HorizontalDivider(
                                color = RosaDetalle
                            )

                            FilaDetalle(
                                "Correo",
                                dato.usuarioEmail.orEmpty()
                            )

                            FilaDetalle(
                                "Evento",
                                dato.tipoEvento.orEmpty()
                            )

                            FilaDetalle(
                                "Fecha",
                                fechaDetalle(dato.fecha)
                            )

                            FilaDetalle(
                                "Hora",
                                dato.hora.orEmpty()
                            )

                            FilaDetalle(
                                "Invitados",
                                dato.asistentes.toString()
                            )

                            FilaDetalle(
                                "Observaciones",
                                if (dato.tieneObservaciones) {
                                    "Sí"
                                } else {
                                    "No"
                                }
                            )

                            HorizontalDivider(
                                color = RosaDetalle
                            )

                            Text(
                                "Presupuesto total",
                                fontSize = 14.sp,
                                color = TextoDetalle
                            )

                            Text(
                                dineroDetalle(
                                    dato.presupuestoTotal
                                ),
                                fontSize = 25.sp,
                                color = GranateDetalle,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                "Estado del abono: " +
                                        (dato.estadoAbono
                                            ?: "SIN_COMPROBANTE"),
                                fontSize = 13.sp,
                                color = TextoDetalle
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    SeccionDetalleAdmin(
                        titulo = "Pack o productos"
                    ) {
                        if (packs.isEmpty() && productos.isEmpty()) {
                            Text("No hay productos registrados.")
                        }

                        packs.forEach { pack ->
                            Text(
                                "• ${pack.nombrePack ?: "Pack #${pack.packId}"} " +
                                        "x${pack.cantidad}",
                                color = TextoDetalle,
                                fontSize = 14.sp
                            )

                            Text(
                                "Subtotal: ${dineroDetalle(pack.subtotal)}",
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }

                        productos.forEach { producto ->
                            Text(
                                "• ${producto.nombreProducto ?: "Producto #${producto.productoId}"} " +
                                        "x${producto.cantidad}",
                                color = TextoDetalle,
                                fontSize = 14.sp
                            )
                        }

                        FilaDetalle(
                            "Productos totales",
                            dato.presupuestoTotal.let {
                                dato.cantidadProductosTotal.toString()
                            }
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    SeccionDetalleAdmin(
                        titulo = "Pago y comprobante"
                    ) {
                        FilaDetalle(
                            "Abono requerido (50 %)",
                            dineroDetalle(
                                dato.presupuestoTotal / 2
                            )
                        )

                        FilaDetalle(
                            "Estado del pago",
                            pagoAbono?.estadoPago
                                ?: "Sin pago registrado"
                        )

                        FilaDetalle(
                            "Estado del comprobante",
                            comprobanteActual
                                ?.estadoComprobante
                                ?: "Sin comprobante"
                        )

                        if (comprobanteActual != null) {
                            Button(
                                onClick = {
                                    onVerComprobante(
                                        comprobanteActual.id
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GranateDetalle
                                )
                            ) {
                                Text("Ver comprobante")
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    if (procesando) {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth(),
                            color = GranateDetalle
                        )
                    }

                    if (dato.estadoReserva == "PENDIENTE") {
                        Button(
                            onClick = {
                                accionPendiente = "CONFIRMAR"
                            },
                            enabled = !procesando,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GranateDetalle
                            )
                        ) {
                            Text("Confirmar reserva")
                        }
                    }

                    if (dato.estadoReserva != "CANCELADA") {
                        OutlinedButton(
                            onClick = {
                                accionPendiente = "CANCELAR"
                            },
                            enabled = !procesando,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = GranateDetalle
                            )
                        ) {
                            Text("Cancelar reserva")
                        }
                    }

                    Spacer(Modifier.height(18.dp))
                }

                if (error != null) {
                    Text(
                        error.orEmpty(),
                        color = Color.Red,
                        fontSize = 12.sp
                    )

                    TextButton(
                        onClick = {
                            actualizar++
                        }
                    ) {
                        Text("Reintentar")
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }

        FooterAdmin(
            seccionActual = "Reservas",
            onCalendarioClick = {
                onNavegar("Calendario")
            },
            onProductosClick = {
                onNavegar("Productos")
            },
            onCatalogoClick = {
                onNavegar("Catálogo")
            },
            onReservasClick = {
                onNavegar("Reservas")
            },
            onPersonalClick = {
                onNavegar("Personal")
            },
            onPerfilClick = {
                onNavegar("Perfil")
            }
        )
    }
}

@Composable
private fun FilaDetalle(
    nombre: String,
    valor: String
) {
    Column {
        Text(
            nombre,
            color = Color(0xFF8B6472),
            fontSize = 12.sp
        )

        Text(
            valor,
            color = TextoDetalle,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun EtiquetaDetalle(
    estado: String
) {
    val fondo = when (estado) {
        "CONFIRMADA" -> Color(0xFFD2EDDB)
        "CANCELADA" -> Color(0xFFFFD3D9)
        else -> Color(0xFFFFE3B5)
    }

    Surface(
        color = fondo,
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            estado,
            color = TextoDetalle,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 7.dp
            )
        )
    }
}

@Composable
private fun SeccionDetalleAdmin(
    titulo: String,
    contenido: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFAFB)
        )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(RosaDetalle)
                    .padding(15.dp)
            ) {
                Text(
                    titulo,
                    color = TextoDetalle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                content = contenido
            )
        }
    }
}
