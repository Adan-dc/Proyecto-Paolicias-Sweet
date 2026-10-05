package com.example.app_en_desarollo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.Cliente
import com.example.app_en_desarollo.Evento
import com.example.app_en_desarollo.Reservas


@Composable
fun FormularioReserva() {
    val context = LocalContext.current

    var Fecha by remember {
        mutableStateOf("")
    }

    var BloqueHorario by remember {
        mutableStateOf("")
    }

    var CantidadAsistentes by remember {
        mutableStateOf(10)
    }

    var Observaciones by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFC5D3)),
        contentAlignment = Alignment.Center

    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Datos del cliente",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = Fecha,
                onValueChange = {
                    Fecha = it
                },
                label = {
                    Text("Fecha")
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = BloqueHorario,
                onValueChange = {
                    BloqueHorario = it
                },
                label = {
                    Text("Bloque horario")
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Text(
                    text = "Cantidad de asistentes: ",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )

                Button(
                    onClick = {
                        if (CantidadAsistentes > 10) {
                            CantidadAsistentes -= 10
                        }
                    }
                ) {
                    Text("-")
                }

                Text(
                    text = "$CantidadAsistentes personas",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )

                Button(
                    onClick = {
                        if (CantidadAsistentes < 100) {
                            CantidadAsistentes += 10
                        }
                    }
                ) {
                    Text("+")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = Observaciones,
                onValueChange = {
                    Observaciones = it
                },
                label = {
                    Text("Observaciones")
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    val reserva = Evento(
                        IDEvento = Reservas.generarId(),
                        Fecha = Fecha,
                        BloqueHorario = BloqueHorario,
                        CantAsistentes = CantidadAsistentes,
                        Observaciones = Observaciones
                    )
                }
            ) {
                Text("Continuar")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FormularioReservaPreview() {
    FormularioReserva()
}