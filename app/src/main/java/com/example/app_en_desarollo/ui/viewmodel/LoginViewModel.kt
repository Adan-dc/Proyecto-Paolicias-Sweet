package com.example.app_en_desarollo.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_en_desarollo.data.database.AppDatabase
import com.example.app_en_desarollo.data.local.entity.ClienteEntity
import com.example.app_en_desarollo.data.local.entity.EmpresaEntity
import com.example.app_en_desarollo.data.local.entity.UserEntity
import com.example.app_en_desarollo.model.ClienteResponse
import com.example.app_en_desarollo.model.EmpresaLoginRequest
import com.example.app_en_desarollo.model.EmpresaResponse
import com.example.app_en_desarollo.model.LoginModel
import com.example.app_en_desarollo.model.LoginRequest
import com.example.app_en_desarollo.model.TipoUsuario
import com.example.app_en_desarollo.network.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {

    var uiState by mutableStateOf(LoginModel())
        private set

    fun onTipoUsuarioChanged(tipo: TipoUsuario) {
        uiState = uiState.copy(
            tipoUsuario = tipo,
            errorMessage = null
        )
    }

    fun onRutChanged(rut: String) {
        uiState = uiState.copy(
            rut = rut,
            rutError = null,
            errorMessage = null
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

    private fun manejarLoginIncorrecto() {
        val nuevosIntentos = uiState.intentosFallidos + 1

        if (nuevosIntentos >= 3) {
            uiState = uiState.copy(
                intentosFallidos = nuevosIntentos,
                bloqueado = true,
                errorMessage = "Demasiados intentos, intenta nuevamente en 10 segundos"
            )

            viewModelScope.launch {
                delay(10000)
                uiState = uiState.copy(
                    intentosFallidos = 0,
                    bloqueado = false,
                    errorMessage = null
                )
            }
        } else {
            uiState = uiState.copy(
                intentosFallidos = nuevosIntentos,
                errorMessage = "Usuario o contraseña incorrectos."
            )
        }
    }

    private fun loginCorrecto() {
        uiState = uiState.copy(
            intentosFallidos = 0,
            bloqueado = false,
            emailError = null,
            rutError = null,
            passwordError = null,
            errorMessage = null
        )
    }

    private suspend fun guardarClienteLocal(
        db: AppDatabase,
        cliente: ClienteResponse,
        password: String
    ) {
        val email = cliente.email ?: return
        if (db.clienteDao().buscarPorEmail(email) != null) return

        val idUsuario = db.usuarioDao().insertarUsuario(
            UserEntity(
                telefono = cliente.telefono ?: "",
                password = password
            )
        ).toInt()

        db.clienteDao().insertarCliente(
            ClienteEntity(
                idUsuario = idUsuario,
                nombre = cliente.nombre ?: "",
                email = email
            )
        )
    }

    private suspend fun guardarEmpresaLocal(
        db: AppDatabase,
        empresa: EmpresaResponse,
        password: String
    ) {
        val rut = empresa.rut ?: return
        if (db.empresaDao().buscarPorRut(rut) != null) return

        val idUsuario = db.usuarioDao().insertarUsuario(
            UserEntity(
                telefono = empresa.telefono ?: "",
                password = password
            )
        ).toInt()

        db.empresaDao().insertarEmpresa(
            EmpresaEntity(
                idUsuario = idUsuario,
                rut = rut,
                razonSocial = empresa.razonSocial ?: ""
            )
        )
    }

    fun onLoginClick(
        context: Context,
        onSuccess: (TipoUsuario) -> Unit
    ) {
        val state = uiState

        if (state.bloqueado) {
            return
        }

        if (state.tipoUsuario == TipoUsuario.CLIENTE) {
            if (state.email.isBlank()) {
                uiState = uiState.copy(emailError = "Se debe INGRESAR el CORREO")
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
                uiState = uiState.copy(rutError = "Se debe INGRESAR el RUT")
                return
            }
        }

        if (state.password.isBlank()) {
            uiState = uiState.copy(passwordError = "Se debe INGRESAR la Contraseña")
            return
        }

        viewModelScope.launch {
            try {
                val db = AppDatabase.getDatabase(context)

                if (state.tipoUsuario == TipoUsuario.CLIENTE) {
                    val cliente = RetrofitClient.clienteApi.login(
                        LoginRequest(state.email, state.password)
                    )
                    guardarClienteLocal(db, cliente, state.password)

                    val tipo = if (cliente.rol == "ADMIN") TipoUsuario.ADMIN else TipoUsuario.CLIENTE
                    loginCorrecto()
                    onSuccess(tipo)
                } else {
                    val empresa = RetrofitClient.empresaApi.login(
                        EmpresaLoginRequest(state.rut, state.password)
                    )
                    guardarEmpresaLocal(db, empresa, state.password)

                    loginCorrecto()
                    onSuccess(TipoUsuario.EMPRESA)
                }
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    manejarLoginIncorrecto()
                } else {
                    uiState = uiState.copy(errorMessage = "Error del servidor, intenta más tarde.")
                }
            } catch (e: Exception) {
                uiState = uiState.copy(errorMessage = "No se pudo conectar con el servidor.")
            }
        }
    }
}