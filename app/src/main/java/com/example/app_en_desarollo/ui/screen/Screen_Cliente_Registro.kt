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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_en_desarollo.ui.components.Footer
import com.example.app_en_desarollo.ui.components.Header
import com.example.app_en_desarollo.ui.viewmodel.RegisterViewModel

@Composable
fun FormularioRegistroCliente(
    onNavigateToSesion: () -> Unit,

    onNavigateToMenu: () -> Unit,
    onNavigateToCategoria: () -> Unit,
    onNavigateToReserva: () -> Unit,
    onNavigateToPerfil: () -> Unit,

    viewModel: RegisterViewModel = viewModel()

) {

    val context = LocalContext.current
    val state = viewModel.uiState

    Scaffold(
        topBar = { Header(title = "REGISTRO USUARIO") },
        bottomBar = {
            Footer(
                onInicioClick = {
                    onNavigateToMenu()
                },

                onCatalogoClick = {
                    onNavigateToCategoria()
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

                TextField(
                    value = state.name,
                    onValueChange = {
                        viewModel.onNameChanged(it)
                    },
                    label = {
                        Text("Nombre")
                    },
                    isError = state.nameError != null,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Contraseña"
                        )
                    },
                    supportingText = {
                        state.nameError?.let { mensaje -> Text(mensaje) }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                //el nombre de la empresa es opcional y sin errores
                TextField(
                    value = state.empresa,
                    onValueChange = {
                        viewModel.onEmpresaChanged(it)
                    },
                    label = {
                        Text("Opcional: Nombre Empresa")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Domain,
                            contentDescription = "Contraseña"
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = state.telefono,
                    onValueChange = {
                        viewModel.onTelefonoChanged(it)
                    },
                    label = {
                        Text("Teléfono (+56 9 XXXXXXXX)")
                    },
                    isError = state.telefonoError != null,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Contacts,
                            contentDescription = "Contraseña"
                        )
                    },
                    supportingText = {
                        state.telefonoError?.let { mensaje -> Text(mensaje) }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = state.email,
                    onValueChange = {
                        viewModel.onEmailChanged(it)
                    },
                    label = {
                        Text("Correo")
                    },
                    isError = state.emailError != null,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Contraseña"
                        )
                    },
                    supportingText = {
                        state.emailError?.let { mensaje -> Text(mensaje) }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                TextField(
                    value = state.password,
                    onValueChange = {
                        viewModel.onPasswordChanged(it)
                    },
                    label = {
                        Text("Contraseña")
                    },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Password,
                            contentDescription = "Contraseña"
                        )
                    },
                    isError = state.passwordError != null,
                    supportingText = {
                        state.passwordError?.let { mensaje -> Text(mensaje) }
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                TextField(
                    value = state.confirmPassword,
                    onValueChange = {
                        viewModel.onConfirmPasswordChanged(it)
                    },
                    label = {
                        Text("Repetir contraseña")
                    },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Password,
                            contentDescription = "Contraseña"
                        )
                    },
                    isError = state.confirmPasswordError != null,
                    supportingText = {
                        state.confirmPasswordError?.let { mensaje -> Text(mensaje) }
                    },
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

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        viewModel.onRegisterClick(
                            context = context,
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    "Se ha registrado correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()

                                vibrarTelefono(context)

                                onNavigateToSesion()
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
    FormularioRegistroCliente(onNavigateToSesion = {}, onNavigateToMenu = {},
        onNavigateToCategoria = {}, onNavigateToReserva = {} ,onNavigateToPerfil = {})
}
