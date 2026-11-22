
package com.example.app_en_desarollo.ui.screen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color as AndroidColor
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.data.api.ComprobanteBackend
import com.example.app_en_desarollo.data.api.PagoBackend
import com.example.app_en_desarollo.data.api.ReservaBackend
import com.example.app_en_desarollo.data.api.RetrofitGeneral
import com.example.app_en_desarollo.ui.components.FooterAdmin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.text.NumberFormat
import java.util.Locale

private val FondoRevision = Color(0xFFFCEAF0)
private val GranateRevision = Color(0xFFA3375D)
private val TextoRevision = Color(0xFF631B36)
private val RosaRevision = Color(0xFFF6DDE6)

private fun formatoMonto(monto: Int?): String {
    if (monto == null) return "No disponible"

    return "$" + NumberFormat.getIntegerInstance(
        Locale("es", "CL")
    ).format(monto)
}

private fun estadoColor(estado: String?): Color {
    return when (estado) {
        "APROBADO" -> Color(0xFFD1EFDB)
        "RECHAZADO" -> Color(0xFFFFD2D8)
        else -> Color(0xFFFFE2B2)
    }
}

private fun cargarPdf(
    bytes: ByteArray,
    carpeta: File
): Bitmap {
    val archivo = File.createTempFile(
        "comprobante_",
        ".pdf",
        carpeta
    )

    try {
        archivo.writeBytes(bytes)

        ParcelFileDescriptor.open(
            archivo,
            ParcelFileDescriptor.MODE_READ_ONLY
        ).use { descriptor ->

            PdfRenderer(descriptor).use { renderer ->
                require(renderer.pageCount > 0) {
                    "El PDF no contiene páginas"
                }

                renderer.openPage(0).use { pagina ->
                    val ancho = 900
                    val alto = (
                            pagina.height.toFloat() *
                                    ancho / pagina.width
                            ).toInt().coerceAtLeast(1)

                    val bitmap = Bitmap.createBitmap(
                        ancho,
                        alto,
                        Bitmap.Config.ARGB_8888
                    )

                    bitmap.eraseColor(AndroidColor.WHITE)

                    pagina.render(
                        bitmap,
                        null,
                        null,
                        PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                    )

                    return bitmap
                }
            }
        }
    } finally {
        archivo.delete()
    }
}

private suspend fun cargarImagenComprobante(
    comprobante: ComprobanteBackend,
    carpeta: File
): Bitmap = withContext(Dispatchers.IO) {

    val bytes = RetrofitGeneral.comprobanteApi
        .descargarArchivo(comprobante.id)
        .use { it.bytes() }

    require(bytes.isNotEmpty()) {
        "El archivo está vacío"
    }

    require(bytes.size <= 12 * 1024 * 1024) {
        "El archivo supera los 12 MB"
    }

    val esPdf =
        comprobante.tipoArchivo == "application/pdf" ||
                comprobante.nombreArchivo.orEmpty()
                    .endsWith(".pdf", ignoreCase = true)

    if (esPdf) {
        cargarPdf(bytes, carpeta)
    } else {
        val opciones = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        BitmapFactory.decodeByteArray(
            bytes,
            0,
            bytes.size,
            opciones
        )

        val dimension = maxOf(
            opciones.outWidth,
            opciones.outHeight
        )

        val escala = (dimension / 1400).coerceAtLeast(1)

        val lectura = BitmapFactory.Options().apply {
            inSampleSize = escala
        }

        BitmapFactory.decodeByteArray(
            bytes,
            0,
            bytes.size,
            lectura
        ) ?: error("No se pudo abrir la imagen")
    }
}

@Composable
fun ComprobantesAdminScreen(
    comprobanteInicial: Long = -1L,
    onVolver: () -> Unit,
    onNavegar: (String) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var comprobantes by remember {
        mutableStateOf<List<ComprobanteBackend>>(emptyList())
    }

    var pagos by remember {
        mutableStateOf<List<PagoBackend>>(emptyList())
    }

    var reservas by remember {
        mutableStateOf<List<ReservaBackend>>(emptyList())
    }

    var seleccionadoId by remember {
        mutableStateOf<Long?>(
            comprobanteInicial.takeIf { it > 0L }
        )
    }

    var imagen by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var cargando by remember { mutableStateOf(true) }
    var cargandoImagen by remember { mutableStateOf(false) }
    var procesando by remember { mutableStateOf(false) }
    var ampliarImagen by remember { mutableStateOf(false) }

    var errorCarga by remember {
        mutableStateOf<String?>(null)
    }

    var errorImagen by remember {
        mutableStateOf<String?>(null)
    }

    var mensajeAccion by remember {
        mutableStateOf<String?>(null)
    }

    var observacion by remember {
        mutableStateOf("")
    }

    var accionPendiente by remember {
        mutableStateOf<String?>(null)
    }

    var recargar by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(recargar) {
        cargando = true
        errorCarga = null

        try {
            val nuevos = RetrofitGeneral.comprobanteApi
                .obtenerTodos()
                .sortedByDescending {
                    it.fechaSubida.orEmpty()
                }

            val nuevosPagos = RetrofitGeneral.pagoApi
                .obtenerTodosLosPagos()

            val nuevasReservas = RetrofitGeneral.reservaApi
                .obtenerTodasLasReservas()

            comprobantes = nuevos
            pagos = nuevosPagos
            reservas = nuevasReservas

            if (
                seleccionadoId == null ||
                nuevos.none { it.id == seleccionadoId }
            ) {
                seleccionadoId = nuevos.firstOrNull {
                    it.estadoComprobante == "PENDIENTE"
                }?.id ?: nuevos.firstOrNull()?.id
            }
        } catch (e: Exception) {
            errorCarga = e.message
                ?: "No se pudieron consultar los comprobantes"
        } finally {
            cargando = false
        }
    }

    val seleccionado = comprobantes.firstOrNull {
        it.id == seleccionadoId
    }

    val pagoSeleccionado = pagos.firstOrNull {
        it.id == seleccionado?.pagoId
    }

    val reservaSeleccionada = reservas.firstOrNull {
        it.id.toLong() == pagoSeleccionado?.reservaId
    }

    val pendientes = comprobantes.filter {
        it.estadoComprobante == "PENDIENTE"
    }

    val revisados = comprobantes.filter {
        it.estadoComprobante == "APROBADO" ||
                it.estadoComprobante == "RECHAZADO"
    }

    LaunchedEffect(seleccionado?.id) {
        val actual = seleccionado ?: return@LaunchedEffect

        imagen = null
        errorImagen = null
        cargandoImagen = true

        try {
            imagen = cargarImagenComprobante(
                actual,
                context.cacheDir
            )
        } catch (e: Exception) {
            errorImagen = e.message
                ?: "No se pudo cargar el archivo"
        } finally {
            cargandoImagen = false
        }
    }

    if (ampliarImagen && imagen != null) {
        AlertDialog(
            onDismissRequest = {
                ampliarImagen = false
            },
            title = {
                Text("Comprobante #${seleccionado?.id}")
            },
            text = {
                Image(
                    bitmap = imagen!!.asImageBitmap(),
                    contentDescription = "Comprobante ampliado",
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 460.dp),
                    contentScale = ContentScale.Fit
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        ampliarImagen = false
                    }
                ) {
                    Text(
                        "Cerrar",
                        color = GranateRevision
                    )
                }
            }
        )
    }

    val actual = seleccionado

    if (accionPendiente != null && actual != null) {
        AlertDialog(
            onDismissRequest = {
                if (!procesando) {
                    accionPendiente = null
                }
            },
            title = {
                Text(
                    if (accionPendiente == "APROBAR") {
                        "Aprobar transferencia"
                    } else {
                        "Rechazar transferencia"
                    }
                )
            },
            text = {
                Text(
                    if (accionPendiente == "APROBAR") {
                        "¿Confirmas que verificaste el comprobante #${actual.id}?"
                    } else {
                        "¿Confirmas el rechazo del comprobante #${actual.id}?"
                    }
                )
            },
            confirmButton = {
                TextButton(
                    enabled = !procesando,
                    onClick = {
                        val accion = accionPendiente
                        val motivo = observacion.trim()

                        procesando = true
                        mensajeAccion = null

                        scope.launch {
                            try {
                                val pago = pagos.firstOrNull {
                                    it.id == actual.pagoId
                                } ?: error(
                                    "No se encontró el pago asociado"
                                )

                                require(
                                    pago.estadoPago == "PENDIENTE"
                                ) {
                                    "El pago ya no está pendiente"
                                }

                                if (accion == "APROBAR") {
                                    Log.d(
                                        "PAOLICIAS_REVISION",
                                        "Aprobando comprobante ${actual.id}"
                                    )

                                    RetrofitGeneral.comprobanteApi
                                        .aprobarComprobante(actual.id)

                                    Log.d(
                                        "PAOLICIAS_REVISION",
                                        "Comprobante aprobado correctamente"
                                    )

                                    Log.d(
                                        "PAOLICIAS_REVISION",
                                        "Aprobando pago ${pago.id}"
                                    )

                                    RetrofitGeneral.pagoApi
                                        .aprobarPago(pago.id)

                                    Log.d(
                                        "PAOLICIAS_REVISION",
                                        "Pago aprobado correctamente"
                                    )

                                    mensajeAccion =
                                        "Comprobante y pago aprobados"
                                } else {
                                    require(motivo.isNotBlank()) {
                                        "Debes escribir el motivo"
                                    }

                                    RetrofitGeneral.comprobanteApi
                                        .rechazarComprobante(
                                            actual.id,
                                            RequestBody.create(
                                                MediaType.parse("text/plain"),
                                                motivo
                                            )
                                        )

                                    RetrofitGeneral.pagoApi
                                        .rechazarPago(pago.id)

                                    mensajeAccion =
                                        "Comprobante y pago rechazados"
                                }

                                observacion = ""
                            } catch (e: Exception) {
                                mensajeAccion = ""
                                android.util.Log.e("Comprobantes", "Error al procesar el comprobante", e)
                            } finally {
                                procesando = false
                                accionPendiente = null
                                recargar++
                            }
                        }
                    }
                ) {
                    Text(
                        "Confirmar",
                        color = GranateRevision
                    )
                }
            },
            dismissButton = {
                TextButton(
                    enabled = !procesando,
                    onClick = {
                        accionPendiente = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoRevision)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Paolicias Sweet APP",
                        color = TextoRevision,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        "Panel administrador",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }

                Spacer(Modifier.height(12.dp))

                TextButton(onClick = onVolver) {
                    Text(
                        "← Volver",
                        color = GranateRevision
                    )
                }
                Text(
                    "Revisión de comprobantes",
                    color = TextoRevision,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Aprueba o rechaza transferencias enviadas por los clientes.",
                    fontSize = 13.sp,
                    color = Color(0xFF765866)
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "Pendientes de revisión",
                    color = TextoRevision,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))

                if (pendientes.isNotEmpty()) {
                    Row(
                        modifier = Modifier.horizontalScroll(
                            rememberScrollState()
                        ),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        pendientes.forEach { comprobante ->
                            FilterChip(
                                selected = seleccionadoId == comprobante.id,
                                onClick = {
                                    seleccionadoId = comprobante.id
                                    observacion = ""
                                    mensajeAccion = null
                                },
                                label = {
                                    Text("Comprobante #${comprobante.id}")
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = GranateRevision,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }
                } else if (!cargando) {
                    Text(
                        "No hay comprobantes pendientes.",
                        color = Color.DarkGray,
                        fontSize = 13.sp
                    )
                }
                if (mensajeAccion != null) {
                    Spacer(Modifier.height(8.dp))

                    Text(
                        mensajeAccion.orEmpty(),
                        color = TextoRevision,
                        fontSize = 12.sp
                    )
                }
                if (errorCarga != null) {
                    Spacer(Modifier.height(8.dp))

                    Text(
                        errorCarga.orEmpty(),
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
                TextButton(
                    onClick = { recargar++ }
                ) {
                    Text(
                        "Actualizar comprobantes",
                        color = GranateRevision
                    )
                }
            }
            if (cargando) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = GranateRevision
                        )
                    }
                }
            } else if (seleccionado != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFFAFB)
                        ),
                        elevation = CardDefaults.cardElevation(3.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(138.dp)
                                        .height(195.dp)
                                        .background(
                                            Color(0xFFF2EDF0),
                                            RoundedCornerShape(14.dp)
                                        )
                                        .clickable(
                                            enabled = imagen != null
                                        ) {
                                            ampliarImagen = true
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    when {
                                        cargandoImagen -> {
                                            CircularProgressIndicator(
                                                color = GranateRevision
                                            )
                                        }
                                        imagen != null -> {
                                            Image(
                                                bitmap = imagen!!.asImageBitmap(),
                                                contentDescription = "Comprobante de transferencia",
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Fit
                                            )
                                        }
                                        else -> {
                                            Text(
                                                errorImagen
                                                    ?: "Sin vista previa",
                                                modifier = Modifier.padding(8.dp),
                                                color = TextoRevision,
                                                fontSize = 11.sp
                                            )
                                        }
                                    }
                                }
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(7.dp)
                                ) {
                                    DatoComprobante(
                                        "Reserva ID",
                                        reservaSeleccionada?.id
                                            ?.toString() ?: "No disponible"
                                    )
                                    DatoComprobante(
                                        "Pago ID",
                                        seleccionado.pagoId.toString()
                                    )
                                    DatoComprobante(
                                        "Cliente",
                                        reservaSeleccionada
                                            ?.usuarioEmail
                                            ?: "No disponible"
                                    )
                                    DatoComprobante(
                                        "Tipo de pago",
                                        pagoSeleccionado?.tipoPago
                                            ?: "No disponible"
                                    )
                                    DatoComprobante(
                                        "Monto",
                                        formatoMonto(
                                            pagoSeleccionado?.monto
                                        )
                                    )
                                }
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    "Estado actual:",
                                    color = TextoRevision,
                                    fontSize = 13.sp
                                )
                                Surface(
                                    color = estadoColor(
                                        seleccionado.estadoComprobante
                                    ),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Text(
                                        seleccionado.estadoComprobante
                                            ?: "PENDIENTE",
                                        color = TextoRevision,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 7.dp
                                        )
                                    )
                                }
                            }
                            Text(
                                "Archivo: ${
                                    seleccionado.nombreArchivo ?: "Adjunto"
                                }",
                                color = Color(0xFF765866),
                                fontSize = 11.sp
                            )
                            if (
                                seleccionado.estadoComprobante == "PENDIENTE"
                            ) {
                                HorizontalDivider(
                                    color = RosaRevision
                                )
                                Text(
                                    "Observación admin",
                                    color = TextoRevision,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                OutlinedTextField(
                                    value = observacion,
                                    onValueChange = {
                                        observacion = it
                                    },
                                    placeholder = {
                                        Text(
                                            "Escribe una observación si rechazas el comprobante"
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    minLines = 3,
                                    maxLines = 4,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            accionPendiente = "RECHAZAR"
                                        },
                                        enabled = !procesando &&
                                                observacion.isNotBlank() &&
                                                pagoSeleccionado?.estadoPago == "PENDIENTE",
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            "Rechazar",
                                            color = GranateRevision
                                        )
                                    }
                                    Button(
                                        onClick = {
                                            accionPendiente = "APROBAR"
                                        },
                                        enabled = !procesando &&
                                                pagoSeleccionado?.estadoPago == "PENDIENTE",
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = GranateRevision
                                        )
                                    ) {
                                        Text("Aprobar")
                                    }
                                }
                                if (procesando) {
                                    LinearProgressIndicator(
                                        modifier = Modifier.fillMaxWidth(),
                                        color = GranateRevision
                                    )
                                }
                            } else {
                                Text(
                                    "Observación: ${
                                        seleccionado.observacionAdmin
                                            ?: "Sin observaciones"
                                    }",
                                    color = TextoRevision,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
            item {
                Text(
                    "Revisados recientemente",
                    color = TextoRevision,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            if (revisados.isEmpty() && !cargando) {
                item {
                    Text(
                        "Todavía no hay comprobantes revisados.",
                        color = Color.DarkGray,
                        fontSize = 13.sp
                    )
                }
            } else {
                items(
                    revisados,
                    key = { it.id }
                ) { comprobante ->
                    val pago = pagos.firstOrNull {
                        it.id == comprobante.pagoId
                    }

                    val reserva = reservas.firstOrNull {
                        it.id.toLong() == pago?.reservaId
                    }
                    Card(
                        onClick = {
                            seleccionadoId = comprobante.id
                            observacion = ""
                            mensajeAccion = null
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFFAFB)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    "Reserva ID: ${
                                        pago?.reservaId ?: "No disponible"
                                    }  |  Pago ID: ${comprobante.pagoId}",
                                    color = TextoRevision,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Text(
                                    reserva?.usuarioEmail
                                        ?: "Cliente no disponible",
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    "${formatoMonto(pago?.monto)}  |  ${
                                        pago?.tipoPago ?: "Pago"
                                    }",
                                    fontSize = 11.sp
                                )
                                Text(
                                    comprobante.fechaSubida
                                        ?.replace("T", " ")
                                        ?.take(16)
                                        ?: "Fecha no disponible",
                                    fontSize = 10.sp,
                                    color = Color.Gray
                                )
                            }
                            Surface(
                                color = estadoColor(
                                    comprobante.estadoComprobante
                                ),
                                shape = RoundedCornerShape(18.dp)
                            ) {
                                Text(
                                    comprobante.estadoComprobante.orEmpty(),
                                    color = TextoRevision,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp,
                                    modifier = Modifier.padding(
                                        horizontal = 8.dp,
                                        vertical = 6.dp
                                    )
                                )
                            }
                        }
                    }
                }
            }
            item {
                Spacer(Modifier.height(8.dp))
            }
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
private fun DatoComprobante(
    titulo: String,
    valor: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            titulo,
            color = Color(0xFF896B76),
            fontSize = 10.sp
        )
        Text(
            valor,
            color = TextoRevision,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}