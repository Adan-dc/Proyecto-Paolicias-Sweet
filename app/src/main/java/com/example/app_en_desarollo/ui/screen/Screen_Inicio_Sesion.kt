package com.example.app_en_desarollo.ui.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_en_desarollo.R
import com.example.app_en_desarollo.ui.components.Footer
import com.example.app_en_desarollo.ui.components.Header
import com.example.app_en_desarollo.ui.viewmodel.LoginViewModel
import com.example.app_en_desarollo.data.preferences.SesionBiometrica

@Composable
fun FormularioSesionCliente(
    onNavigateToRegister: () -> Unit,
    onNavigateToMenu: () -> Unit,

    onNavigateToCatalogo: () -> Unit,
    onNavigateToReserva: () -> Unit,
    onNavigateToPerfil: () -> Unit,

    viewModel: LoginViewModel = viewModel()

) {
    val context = LocalContext.current
    val state = viewModel.uiState
    val activity = context as FragmentActivity
    Scaffold(
        topBar = { Header(title = "INICIO SESIÓN") },
        bottomBar = {
            Footer(
                onInicioClick = {
                    onNavigateToMenu()
                },

                onCatalogoClick = {
                    onNavigateToCatalogo()
                },

                onReservaClick = {
                    onNavigateToReserva()
                },

                onPerfilClick = {
                    onNavigateToPerfil()
                }
            )
        }
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
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Image(
                    painter = painterResource(id = R.drawable.emblema_paolicias_sweet),
                    contentDescription = "Imagen de super heroe",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .shadow(elevation = 12.dp, shape = RectangleShape)
                        .clip(RectangleShape)
                        .border(4.dp, Color.Black, RectangleShape)
                )

                Spacer(modifier = Modifier.height(32.dp))

                TextField(
                    value = state.email,
                    onValueChange = { viewModel.onEmailChanged(it) },
                    label = { Text("Correo Electrónico") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = state.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                state.errorMessage?.let { mensaje ->

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = mensaje,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        viewModel.onLoginClick(
                            context = context,
                            onSuccess = {

                                SesionBiometrica.guardarSesion(
                                    context,
                                    state.email
                                )

                                Toast.makeText(
                                    context,
                                    "Inicio de sesión correcto",
                                    Toast.LENGTH_SHORT
                                ).show()

                                vibrarTelefono(context)

                                onNavigateToCatalogo()
                            }
                        )
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
                        letterSpacing = 2.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {


                        if (!SesionBiometrica.sesionVigente(context)) {

                            Toast.makeText(
                                context,
                                "La sesión expiró. Ingresa nuevamente tu contraseña.",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else if (biometriaDisponible(activity)) {

                            inicarBiomeria(
                                activity = activity,

                                onSuccess = {

                                    Toast.makeText(
                                        context,
                                        "Huella reconocida",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    vibrarTelefono(context)

                                    onNavigateToCatalogo()
                                },

                                onError = { mensaje ->

                                    Toast.makeText(
                                        context,
                                        mensaje,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )

                        } else {

                            Toast.makeText(
                                context,
                                "Biometría no disponible",
                                Toast.LENGTH_SHORT
                            ).show()
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
                        text = "Ingresar con huella",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "No tienes cuenta, crea una 😁",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

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
                        letterSpacing = 2.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FormularioSesionPreview() {
    FormularioSesionCliente(onNavigateToRegister = {}, onNavigateToMenu= {},
        onNavigateToCatalogo = {}, onNavigateToReserva = {} ,onNavigateToPerfil = {})
}