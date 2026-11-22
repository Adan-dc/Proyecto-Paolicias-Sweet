
package com.example.app_en_desarollo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.data.api.RetrofitGeneral
import com.example.app_en_desarollo.ui.components.FooterAdmin
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

private val fondoCalendario = Color(0xFFFBE9EC)
private val granateCalendario = Color(0xFF8D193F)
private val botonCalendario = Color(0xFFA3375D)
private val rosaCalendario = Color(0xFFF5D7E0)
private val textoCalendario = Color(0xFF73525E)

private data class EventoCalendario(
    val id: Int,
    val cliente: String,
    val tipoEvento: String,
    val fecha: LocalDate,
    val hora: String,
    val asistentes: Int,
    val estado: String
)

@Composable
fun CalendarioAdminScreen(
    onVerDetalle: (Int) -> Unit,
    onNavegar: (String) -> Unit
) {
    val hoy = remember { LocalDate.now() }

    var mesActual by remember {
        mutableStateOf(YearMonth.from(hoy))
    }

    var diaSeleccionado by remember {
        mutableStateOf<LocalDate?>(null)
    }

    var eventos by remember {
        mutableStateOf<List<EventoCalendario>>(emptyList())
    }

    var cargando by remember {
        mutableStateOf(true)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    var recargar by remember {
        mutableIntStateOf(0)
    }

    var mesInicializado by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(recargar) {
        cargando = true
        error = null

        try {
            val respuesta = RetrofitGeneral.reservaApi
                .obtenerTodasLasReservas()

            val lista = respuesta.mapNotNull { reserva ->
                val fecha = runCatching {
                    LocalDate.parse(
                        reserva.fecha.orEmpty()
                    )
                }.getOrNull()

                if (fecha == null) {
                    null
                } else {
                    EventoCalendario(
                        id = reserva.id.toInt(),
                        cliente = reserva.usuarioEmail.orEmpty(),
                        tipoEvento = reserva.tipoEvento.orEmpty(),
                        fecha = fecha,
                        hora = reserva.hora.orEmpty().take(5),
                        asistentes = reserva.asistentes,
                        estado = reserva.estadoReserva.orEmpty()
                    )
                }
            }.sortedWith(
                compareBy<EventoCalendario> { it.fecha }
                    .thenBy { it.hora }
                    .thenBy { it.id }
            )

            eventos = lista

            if (!mesInicializado) {
                val siguiente = lista.firstOrNull {
                    !it.fecha.isBefore(hoy) &&
                            !it.estado.equals(
                                "CANCELADA",
                                ignoreCase = true
                            )
                }

                if (
                    lista.none {
                        YearMonth.from(it.fecha) == mesActual
                    } && siguiente != null
                ) {
                    mesActual = YearMonth.from(siguiente.fecha)
                }

                mesInicializado = true
            }
        } catch (e: Exception) {
            error = e.message
                ?: "No se pudieron cargar las reservas"
        } finally {
            cargando = false
        }
    }

    val eventosMes = eventos.filter {
        YearMonth.from(it.fecha) == mesActual
    }

    val eventosVisibles = if (diaSeleccionado != null) {
        eventosMes.filter {
            it.fecha == diaSeleccionado
        }
    } else {
        eventosMes
    }

    val diasConReservas = eventosMes
        .map { it.fecha.dayOfMonth }
        .toSet()

    val tituloListado = if (diaSeleccionado != null) {
        "Reservas del día"
    } else {
        "Próximas reservas"
    }

    Scaffold(
        containerColor = fondoCalendario,
        bottomBar = {
            FooterAdmin(seleccionado = "Calendario")
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 14.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Paolicias Sweet APP",
                        color = granateCalendario,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Panel administrador",
                        color = textoCalendario,
                        fontSize = 12.sp
                    )
                }
            }

            item {
                Column {
                    Text(
                        text = "Calendario",
                        color = granateCalendario,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Revisa las reservas por fecha y las próximas del mes.",
                        color = textoCalendario,
                        fontSize = 13.sp
                    )
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFF7F9)
                    ),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    mesActual = mesActual.minusMonths(1)
                                    diaSeleccionado = null
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ChevronLeft,
                                    contentDescription = "Mes anterior",
                                    tint = granateCalendario
                                )
                            }

                            Text(
                                text = nombreMes(mesActual),
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = granateCalendario
                            )

                            IconButton(
                                onClick = {
                                    mesActual = mesActual.plusMonths(1)
                                    diaSeleccionado = null
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = "Mes siguiente",
                                    tint = granateCalendario
                                )
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(rosaCalendario)
                                .padding(vertical = 12.dp)
                        ) {
                            listOf(
                                "Lun",
                                "Mar",
                                "Mié",
                                "Jue",
                                "Vie",
                                "Sáb",
                                "Dom"
                            ).forEach { dia ->
                                Text(
                                    text = dia,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = granateCalendario
                                )
                            }
                        }

                        Spacer(Modifier.height(12.dp))

                        CuadriculaCalendario(
                            mes = mesActual,
                            diasConReservas = diasConReservas,
                            diaSeleccionado = diaSeleccionado,
                            onSeleccionarDia = { fecha ->
                                diaSeleccionado = if (
                                    diaSeleccionado == fecha
                                ) {
                                    null
                                } else {
                                    fecha
                                }
                            }
                        )
                    }
                }
            }

            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = tituloListado,
                            color = granateCalendario,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        if (diaSeleccionado != null) {
                            TextButton(
                                onClick = {
                                    diaSeleccionado = null
                                }
                            ) {
                                Text(
                                    text = "Ver todas",
                                    color = botonCalendario
                                )
                            }
                        }
                    }

                    if (diaSeleccionado != null) {
                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = diaSeleccionado!!.format(
                                DateTimeFormatter.ofPattern(
                                    "dd/MM/yyyy"
                                )
                            ),
                            color = textoCalendario,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            if (cargando) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = botonCalendario
                        )
                    }
                }
            } else if (error != null) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = error.orEmpty(),
                            color = Color(0xFFB3261E),
                            fontSize = 13.sp
                        )

                        TextButton(
                            onClick = {
                                recargar++
                            }
                        ) {
                            Text(
                                text = "Reintentar",
                                color = botonCalendario
                            )
                        }
                    }
                }
            } else if (eventosVisibles.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFF7F9)
                        )
                    ) {
                        Text(
                            text = "No hay reservas para esta selección.",
                            modifier = Modifier.padding(20.dp),
                            fontSize = 13.sp,
                            color = textoCalendario
                        )
                    }
                }
            } else {
                items(
                    eventosVisibles,
                    key = { it.id }
                ) { evento ->
                    TarjetaEventoCalendario(
                        evento = evento,
                        onVerDetalle = {
                            onVerDetalle(evento.id)
                        }
                    )
                }
            }

            item {
                TextButton(
                    onClick = {
                        recargar++
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Actualizar calendario",
                        color = botonCalendario
                    )
                }
            }
        }
    }
}

@Composable
private fun CuadriculaCalendario(
    mes: YearMonth,
    diasConReservas: Set<Int>,
    diaSeleccionado: LocalDate?,
    onSeleccionarDia: (LocalDate) -> Unit
) {
    val celdas = remember(mes) {
        val fechas = mutableListOf<LocalDate?>()

        val espaciosIniciales = mes.atDay(1)
            .dayOfWeek.value - 1

        repeat(espaciosIniciales) {
            fechas.add(null)
        }

        for (numero in 1..mes.lengthOfMonth()) {
            fechas.add(mes.atDay(numero))
        }

        while (fechas.size % 7 != 0) {
            fechas.add(null)
        }

        fechas
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        celdas.chunked(7).forEach { semana ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                semana.forEach { fecha ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(43.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (fecha != null) {
                            val tieneReserva =
                                fecha.dayOfMonth in diasConReservas

                            val seleccionado =
                                fecha == diaSeleccionado

                            val resaltado =
                                tieneReserva || seleccionado

                            Box(
                                modifier = Modifier
                                    .size(39.dp)
                                    .clip(
                                        RoundedCornerShape(12.dp)
                                    )
                                    .background(
                                        when {
                                            seleccionado ->
                                                Color(0xFF75112F)

                                            tieneReserva ->
                                                botonCalendario

                                            else ->
                                                Color.Transparent
                                        }
                                    )
                                    .clickable {
                                        onSeleccionarDia(fecha)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = fecha.dayOfMonth.toString(),
                                    fontSize = 15.sp,
                                    fontWeight = if (resaltado) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Normal
                                    },
                                    color = if (resaltado) {
                                        Color.White
                                    } else {
                                        granateCalendario
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TarjetaEventoCalendario(
    evento: EventoCalendario,
    onVerDetalle: () -> Unit
) {
    val colorEstado = when (
        evento.estado.uppercase()
    ) {
        "CONFIRMADA" -> Color(0xFFD0ECD8)
        "CANCELADA" -> Color(0xFFF9CDD5)
        else -> Color(0xFFFFE5B8)
    }

    val textoEstado = when (
        evento.estado.uppercase()
    ) {
        "CONFIRMADA" -> Color(0xFF175C35)
        "CANCELADA" -> Color(0xFFB32634)
        else -> Color(0xFF8B4B14)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF9FA)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(75.dp)
                        .height(95.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF0C7D4)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(38.dp),
                        tint = granateCalendario
                    )
                }

                Column(
                    modifier = Modifier
                        .width(51.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(rosaCalendario)
                        .padding(
                            horizontal = 4.dp,
                            vertical = 10.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = evento.fecha.dayOfMonth.toString(),
                        color = granateCalendario,
                        fontWeight = FontWeight.Bold,
                        fontSize = 23.sp
                    )

                    Text(
                        text = evento.fecha.month.getDisplayName(
                            TextStyle.SHORT,
                            Locale("es", "CL")
                        ).replace(".", "").replaceFirstChar {
                            it.uppercase()
                        },
                        fontSize = 11.sp,
                        color = granateCalendario
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = evento.cliente,
                        color = granateCalendario,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = evento.tipoEvento,
                        color = textoCalendario,
                        fontSize = 13.sp,
                        maxLines = 2
                    )

                    Text(
                        text = "Hora: ${evento.hora}",
                        color = textoCalendario,
                        fontSize = 12.sp
                    )

                    Text(
                        text = "${evento.asistentes} invitados",
                        color = textoCalendario,
                        fontSize = 12.sp
                    )

                    Text(
                        text = "Reserva #${evento.id}",
                        color = textoCalendario,
                        fontSize = 11.sp
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = colorEstado
                ) {
                    Text(
                        text = evento.estado.uppercase(),
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 8.dp
                        ),
                        color = textoEstado,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = onVerDetalle,
                    modifier = Modifier
                        .width(135.dp)
                        .height(39.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = botonCalendario
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Ver detalle",
                        fontSize = 13.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

private fun nombreMes(mes: YearMonth): String {
    val locale = Locale("es", "CL")

    val nombre = mes.month.getDisplayName(
        TextStyle.FULL,
        locale
    ).replaceFirstChar {
        it.titlecase(locale)
    }

    return "$nombre ${mes.year}"
}
