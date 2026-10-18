package com.matias.paoliciassweet.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.matias.paoliciassweet.Componentes.Footer
import com.matias.paoliciassweet.Componentes.Header
import com.matias.paoliciassweet.model.MAX_ASISTENTES
import java.text.NumberFormat
import java.util.Locale
import com.matias.paoliciassweet.ui.viewmodel.ReservaViewModel


@Composable
fun ReservaScreen(
    onNavigateToObservaciones: () -> Unit,
    onNavigateToCatalogo: () -> Unit = {},
    viewModel: ReservaViewModel = viewModel()
){
    val state = viewModel.uiState
    val context = LocalContext.current

    Scaffold(
        containerColor = Color(0xFFFBE9EC),
        topBar = { Header(title = "Paolicias-Sweet APP", backgroundColor = Color(0xFFFBE9EC), textColor = Color(0xFF5A1F33)) },
        bottomBar = {
            Footer(
                onInicioClick = {},
                onReservaClick = {},
                onCatalogoClick = {},
                onPerfilClick = {}
            )
        }
    ) {
            innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "Evento",
                style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFF5A1F33)
            )
            Text(
                text = "Selecciona las opciones que consideras para tu evento.",
                fontSize = 12.sp,
                color = Color(0xFF8A6A72)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Opciones",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF5A1F33)
            )
            Text(
                text = "Las opciones que ya estén listas se marcarán automáticamente",
                fontSize = 10.sp,
                color = Color(0xFF8A6A72)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFFFF8F6))
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                FilaOpcion(checked = state.fechaValida, label = "Fecha") {
                    CampoTexto(
                        value = state.fecha,
                        onValueChange = { viewModel.onFechaChanged(it) },
                        placeholder = "DD/MM/YYYY",
                        modifier = Modifier.width(110.dp),
                        visualTransformation = FechaMascara
                    )
                }
                HorizontalDivider(color = Color(0xFFF4CFD7))

                FilaOpcion(checked = state.horaValida, label = "Hora") {
                    CampoTexto(
                        value = state.hora,
                        onValueChange = { viewModel.onHoraChanged(it) },
                        placeholder = "00:00",
                        modifier = Modifier.width(70.dp),
                        visualTransformation = HoraMascara
                    )
                }
                HorizontalDivider(color = Color(0xFFF4CFD7))

                FilaOpcion(checked = state.asistentesValido, label = "Cantidad de asistentes") {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CampoTexto(
                            value = state.asistentes,
                            onValueChange = { viewModel.onAsistentesChanged(it) },
                            placeholder = "0",
                            modifier = Modifier.width(32.dp)
                        )
                        Text(text = "/$MAX_ASISTENTES", fontSize = 13.sp, color = Color(0xFF8A6A72))
                    }
                }
                HorizontalDivider(color = Color(0xFFF4CFD7))

                FilaOpcion(
                    checked = state.tieneObservaciones,
                    label = "Ingresar observaciones especiales",
                    modifier = Modifier.clickable { onNavigateToObservaciones() }
                ) {
                    BotonFlecha(onClick = { onNavigateToObservaciones() })
                }
                HorizontalDivider(color = Color(0xFFF4CFD7))

                FilaOpcion(
                    checked = false,
                    label = "Revisar catalogo de productos",
                    modifier = Modifier.clickable { onNavigateToCatalogo() }
                ) {
                    BotonFlecha(onClick = { onNavigateToCatalogo() })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Cotización",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF5A1F33)
            )

            Spacer(modifier = Modifier.height(8.dp))

            val monto = NumberFormat
                .getIntegerInstance(Locale.forLanguageTag("es-CL"))
                .format(state.cotizacion)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF4CFD7))
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "Costo de cotización: $" + monto,
                    color = Color(0xFFA63A5B),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if(state.errorMessage != null){
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state.errorMessage,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.onConfirmarReservaClick {
                        Toast.makeText(
                            context,
                            "¡Reserva confirmada!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA63A5B),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Confirmar reserva", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun FilaOpcion(
    checked: Boolean,
    label: String,
    modifier: Modifier = Modifier,
    trailing: @Composable () -> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFFA63A5B),
                uncheckedColor = Color(0xFF8A6A72),
                checkmarkColor = Color.White
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = label,
            fontSize = 13.sp,
            color = Color(0xFF8A6A72),
            modifier = Modifier.weight(1f)
        )
        trailing()
    }
}

@Composable
private fun BotonFlecha(onClick: () -> Unit){
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(Color(0xFFA63A5B))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "↗",
            color = Color.White,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun CampoTexto(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None
){
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        singleLine = true,
        textStyle = TextStyle(color = Color(0xFF5A1F33), fontSize = 13.sp, textAlign = TextAlign.End),
        cursorBrush = SolidColor(Color(0xFFA63A5B)),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = visualTransformation,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ){
                if (value.isEmpty()) {
                    Text(text = placeholder, color = Color(0xFF8A6A72), fontSize = 13.sp)
                }
                innerTextField()
            }
        }
    )
}

private class MascaraTransformation(
    private val separador: Char,
    private val posiciones: List<Int>
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val digitos = text.text
        val salida = buildString {
            digitos.forEachIndexed { i, c ->
                if (i in posiciones) append(separador)
                append(c)
            }
        }
        val indicesSeparador = posiciones.mapIndexed { k, p -> p + k }

        val mapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int =
                (offset + posiciones.count { it < offset }).coerceAtMost(salida.length)

            override fun transformedToOriginal(offset: Int): Int =
                (offset - indicesSeparador.count { it < offset }).coerceIn(0, digitos.length)
        }
        return TransformedText(AnnotatedString(salida), mapping)
    }
}

private val FechaMascara = MascaraTransformation('/', listOf(2, 4))
private val HoraMascara = MascaraTransformation(':', listOf(2))