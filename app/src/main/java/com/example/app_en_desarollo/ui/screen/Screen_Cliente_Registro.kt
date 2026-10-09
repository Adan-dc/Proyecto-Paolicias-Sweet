package com.example.app_en_desarollo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.Cliente
import com.example.app_en_desarollo.ui.components.Footer
import com.example.app_en_desarollo.ui.components.Header

@Composable
fun FormularioCliente(
    onNavigateToSesion: () -> Unit
) {

    val context = LocalContext.current

    var nombre by remember {
        mutableStateOf("")
    }

    var empresa by remember {
        mutableStateOf("")
    }

    var telefono by remember {
        mutableStateOf("")
    }

    var correo by remember {
        mutableStateOf("")
    }

    var contraseña by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = { Header(title = "REGISTRO USUARIO") },
        bottomBar = { Footer() }
    ) {
        innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                TextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                    },
                    label = {
                        Text("Nombre")
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = empresa,
                    onValueChange = {
                        empresa = it
                    },
                    label = {
                        Text("Opcional: Nombre Empresa")
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = telefono,
                    onValueChange = {
                        telefono = it
                    },
                    label = {
                        Text("Teléfono")
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = correo,
                    onValueChange = {
                        correo = it
                    },
                    label = {
                        Text("Correo")
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = contraseña,
                    onValueChange = {
                        contraseña = it
                    },
                    label = {
                        Text("Contraseña")
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        onNavigateToSesion()
                        val cliente = Cliente(
                            NombreCompleto = nombre,
                            Empresa = "",
                            Telefono = telefono,
                            Correo = correo
                        )

                        if (cliente.verificarCorreo()) {
                            Toast.makeText(context, "Correo válido", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Correo inválido", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
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
                        text = "Continuar",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp)
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FormularioPreview() {
    FormularioCliente(onNavigateToSesion= {})
}
