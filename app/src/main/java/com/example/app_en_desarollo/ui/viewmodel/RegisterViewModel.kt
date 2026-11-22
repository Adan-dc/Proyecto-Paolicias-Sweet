package com.example.app_en_desarollo.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_en_desarollo.model.ClienteModel
import com.example.app_en_desarollo.model.EmpresaModel
import com.example.app_en_desarollo.model.RegisterModel
import com.example.app_en_desarollo.model.TipoUsuario
import com.example.app_en_desarollo.data.api.RetrofitGeneral
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel : ViewModel() {

    var uiState by mutableStateOf(RegisterModel())
        private set

    fun onTipoUsuarioChanged(tipo: TipoUsuario) {
        uiState = uiState.copy(
            tipoUsuario = tipo,
            errorMessage = null
        )
    }

    private fun rutEsValido(rut: String): Boolean {
        return Regex("^\\d{1,2}(\\.?\\d{3}){2}-[0-9kK]$").matches(rut.trim())
    }
    fun onRutChanged(rut: String) {
        uiState = uiState.copy(
            rut = rut,
            rutError = null
        )
    }

    fun onRazonSocialChanged(razonSocial: String) {
        uiState = uiState.copy(
            razonSocial = razonSocial,
            razonSocialError = null
        )
    }

    fun onNameChanged(name: String) {
        uiState = uiState.copy(
            name = name,
            errorMessage = null,
            nameError = null
        )
    }

    fun onTelefonoChanged(telefono: String) {
        uiState = uiState.copy(
            telefono = telefono,
            errorMessage = null,
            telefonoError = null
        )
    }

    fun onEmailChanged(email: String) {
        uiState = uiState.copy(
            email = email,
            errorMessage = null,
            emailError = null
        )
    }

    fun onPasswordChanged(password: String) {
        uiState = uiState.copy(
            password = password,
            errorMessage = null,
            passwordError = null
        )
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        uiState = uiState.copy(
            confirmPassword = confirmPassword,
            errorMessage = null,
            confirmPasswordError = null
        )
    }

    fun onRegisterClick(context: Context, onSuccess: () -> Unit) {
        val state = uiState

        if (state.telefono.isBlank()) {
            uiState = uiState.copy(telefonoError = "Debe COLOCAR un TELEFONO")
            return
        }

        if (!state.telefono.startsWith("+56 9") || state.telefono.length != 14) {
            uiState = uiState.copy(
                telefonoError = "El numero debe tener formato +56 9 XXXXXXXX"
            )
            return
        }

        if (state.password.isBlank()) {
            uiState = uiState.copy(passwordError = "Debe COLOCAR una CONTRASEÑA")
            return
        }

        if (state.confirmPassword.isBlank()) {
            uiState = uiState.copy(confirmPasswordError = "se debe CONFIRMAR la CONTRASEÑA")
            return
        }

        if (state.password != state.confirmPassword) {
            uiState = uiState.copy(errorMessage = "Las contraseñas deben se iguales.")
            return
        }

        if (state.tipoUsuario == TipoUsuario.CLIENTE) {

            if (state.name.isBlank()) {
                uiState = uiState.copy(nameError = "Debe COLOCAR un NOMBRE")
                return
            }

            if (state.email.isBlank()) {
                uiState = uiState.copy(emailError = "Debe COLOCAR un CORREO")
                return
            }

            if (!state.email.substringAfter("@").contains(".") || !state.email.contains("@")
                || state.email.endsWith(".") || state.email.endsWith("@")
                || state.email.startsWith(".") || state.email.startsWith("@")
            ) {
                uiState = uiState.copy(emailError = "El correo debe ser valido")
                return
            }

        } else {
            if (state.rut.isBlank()) {
                uiState = uiState.copy(rutError = "Debe COLOCAR un RUT")
                return
            }

            if (!rutEsValido(state.rut)) {
                uiState = uiState.copy(rutError = "El RUT debe tener formato 12.345.678-9")
                return
            }

            if (state.razonSocial.isBlank()) {
                uiState = uiState.copy(razonSocialError = "Debe COLOCAR el RUBRO DE LA EMPRESA")
                return
            }
        }

        viewModelScope.launch {
            try {
                if (state.tipoUsuario == TipoUsuario.CLIENTE) {
                    RetrofitGeneral.clienteApi.registrar(
                        ClienteModel(
                            nombre = state.name,
                            telefono = state.telefono,
                            email = state.email,
                            password = state.password
                        )
                    )
                } else {
                    RetrofitGeneral.empresaApi.registrar(
                        EmpresaModel(
                            rut = state.rut,
                            razonSocial = state.razonSocial,
                            telefono = state.telefono,
                            password = state.password
                        )
                    )
                }

                uiState = uiState.copy(errorMessage = null)
                onSuccess()

            } catch (e: HttpException) {
                uiState = uiState.copy(
                    errorMessage = if (e.code() == 409) {
                        if (state.tipoUsuario == TipoUsuario.CLIENTE) "El correo ya esta registrado"
                        else "El rut ya esta registrado"
                    } else "No se pudo crear la cuenta"
                )
            } catch (e: Exception) {
                uiState = uiState.copy(errorMessage = "No se pudo conectar con el servidor.")
            }
        }
    }
}