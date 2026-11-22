
package com.example.app_en_desarollo.ui.screen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_en_desarollo.data.api.RetrofitGeneral
import com.example.app_en_desarollo.data.preferences.SesionBiometrica
import com.example.app_en_desarollo.ui.components.Footer
import com.example.app_en_desarollo.ui.components.Header
import com.example.app_en_desarollo.ui.viewmodel.ReservaViewModel
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToLong

private val fondoTransferencia = Color(0xFFFBE9EC)
private val tituloTransferencia = Color(0xFF5A1F33)
private val botonTransferencia = Color(0xFFA63A5B)

@Composable
fun TransferenciaScreen(
    reservaId: Int,
    onNavigateBack: () -> Unit = {},
    onNavigateToReservas: () -> Unit = {},
    onNavigateToInicio: () -> Unit = {},
    onNavigateToCatalogo: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {},
    viewModel: ReservaViewModel = viewModel()
) {
    val context = LocalContext.current

    var comprobanteUri by rememberSaveable(reservaId) {
        mutableStateOf("")
    }

    var guardando by rememberSaveable {
        mutableStateOf(false)
    }

    var presupuestoTotal by rememberSaveable(reservaId) {
        mutableStateOf<Double?>(null)
    }

    var cargandoPresupuesto by rememberSaveable(reservaId) {
        mutableStateOf(true)
    }

    var errorPresupuesto by rememberSaveable(reservaId) {
        mutableStateOf<String?>(null)
    }

    val formatoPrecio = NumberFormat.getNumberInstance(
        Locale("es", "CL")
    )

    LaunchedEffect(reservaId) {
        cargandoPresupuesto = true
        presupuestoTotal = null
        errorPresupuesto = null

        try {
            val email = SesionBiometrica.obtenerIdentificador(context)
                .orEmpty()

            if (email.isBlank()) {
                errorPresupuesto = "No se encontró la sesión del usuario"
            } else {
                val reservas = RetrofitGeneral.reservaApi
                    .obtenerReservasPorUsuario(email)

                val reserva = reservas.find {
                    it.id == reservaId
                }

                if (reserva == null) {
                    errorPresupuesto = "No se encontró esta reserva"
                } else {
                    val presupuesto = reserva.presupuestoTotal
                        .toString()
                        .toDoubleOrNull()

                    if (presupuesto != null && presupuesto >= 0) {
                        presupuestoTotal = presupuesto
                    } else {
                        errorPresupuesto =
                            "No se pudo obtener el presupuesto"
                    }
                }
            }
        } catch (e: Exception) {
            errorPresupuesto =
                "No se pudo cargar el presupuesto de la reserva"
        } finally {
            cargandoPresupuesto = false
        }
    }

    val seleccionarComprobante = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                comprobanteUri = uri.toString()
            } catch (e: SecurityException) {
                Toast.makeText(
                    context,
                    "No se pudo acceder al archivo seleccionado",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    Scaffold(
        containerColor = fondoTransferencia,
        topBar = {
            Header(
                title = "Paolicias-Sweet APP",
                backgroundColor = fondoTransferencia,
                textColor = tituloTransferencia
            )
        },
        bottomBar = {
            Footer(
                onInicioClick = onNavigateToInicio,
                onReservaClick = onNavigateToReservas,
                onCatalogoClick = onNavigateToCatalogo,
                onPerfilClick = onNavigateToPerfil
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Text(
                text = "Adjuntar transferencia\nelectrónica",
                color = tituloTransferencia,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                lineHeight = 29.sp
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Registre el abono del 50%",
                color = tituloTransferencia,
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Se marcará la transferencia como exitosa, cuando esta sea revisada.",
                fontSize = 11.sp,
                color = Color(0xFF8A6A72)
            )

            Spacer(Modifier.height(18.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFFFFF8F6))
                    .padding(18.dp)
            ) {
                Text(
                    text = "Reserva #$reservaId",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = tituloTransferencia
                )

                Spacer(Modifier.height(14.dp))

                if (cargandoPresupuesto) {
                    Text(
                        text = "Cargando presupuesto...",
                        fontSize = 13.sp,
                        color = tituloTransferencia
                    )
                } else if (presupuestoTotal != null) {
                    val total = presupuestoTotal!!

                    Text(
                        text = "Presupuesto total",
                        fontSize = 12.sp,
                        color = Color(0xFF8A6A72)
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = "$" + formatoPrecio.format(
                            total.roundToLong()
                        ),
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = tituloTransferencia
                    )

                    Spacer(Modifier.height(14.dp))

                    HorizontalDivider(
                        color = Color(0xFFEBD4DA)
                    )

                    Spacer(Modifier.height(14.dp))

                    Text(
                        text = "Monto a transferir (50%)",
                        fontSize = 13.sp,
                        color = Color(0xFF8A6A72)
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = "$" + formatoPrecio.format(
                            (total * 0.5).roundToLong()
                        ),
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Bold,
                        color = botonTransferencia
                    )
                } else {
                    Text(
                        text = errorPresupuesto
                            ?: "Presupuesto no disponible",
                        fontSize = 12.sp,
                        color = tituloTransferencia
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFFFFF8F6))
                    .clickable {
                        seleccionarComprobante.launch(
                            arrayOf("image/*", "application/pdf")
                        )
                    }
                    .padding(
                        horizontal = 8.dp,
                        vertical = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Checkbox(
                        checked = comprobanteUri.isNotEmpty(),
                        onCheckedChange = {
                            seleccionarComprobante.launch(
                                arrayOf("image/*", "application/pdf")
                            )
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = botonTransferencia
                        ),
                        modifier = Modifier.size(38.dp)
                    )

                    Spacer(Modifier.width(4.dp))

                    Text(
                        text = if (comprobanteUri.isEmpty()) {
                            "Transferencia electrónica"
                        } else {
                            "Comprobante seleccionado"
                        },
                        fontSize = 12.sp,
                        color = tituloTransferencia
                    )
                }

                Row(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(botonTransferencia),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Elegir comprobante",
                        tint = Color.White,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }

            if (comprobanteUri.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Archivo listo para enviar. Se guardará como pendiente de validación.",
                    color = Color(0xFF8A6A72),
                    fontSize = 11.sp
                )
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    if (comprobanteUri.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Selecciona un comprobante primero",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (!guardando) {
                        guardando = true

                        viewModel.guardarComprobante(
                            context = context,
                            reservaId = reservaId,
                            uri = comprobanteUri,
                            onSuccess = {
                                guardando = false

                                Toast.makeText(
                                    context,
                                    "Comprobante guardado, pendiente de validación",
                                    Toast.LENGTH_LONG
                                ).show()

                                onNavigateBack()
                            },
                            onError = {
                                guardando = false

                                Toast.makeText(
                                    context,
                                    "No se pudo guardar el comprobante",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        )
                    }
                },
                enabled = !guardando,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = botonTransferencia
                )
            ) {
                Text(
                    text = if (guardando) {
                        "Guardando..."
                    } else {
                        "Confirmar"
                    },
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
