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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_en_desarollo.Cliente
import com.example.app_en_desarollo.ui.components.Footer
import com.example.app_en_desarollo.ui.components.Header

@Composable
fun FormularioSesionCliente(
    onNavigateToRegister: () -> Unit,
    onNavigateToMenu: () -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        topBar = { Header(title = "INICIO SESIÓN") },
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

                Spacer(modifier = Modifier.height(32.dp))


                Button(
                    onClick = {
                        onNavigateToMenu()
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
                        text = "Inicia Sesión",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp)
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "No tienes cuenta, crea una 😁",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = {
                        onNavigateToRegister()
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
                        text = "Registrate",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp)
                }
            }
                /*

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

                Spacer(modifier = Modifier.height(24.dp))

                 */
            }
        }
}
@Preview(showBackground = true)
@Composable
fun FormularioSesionPreview() {
    FormularioSesionCliente(onNavigateToRegister = {}, onNavigateToMenu= {})
}