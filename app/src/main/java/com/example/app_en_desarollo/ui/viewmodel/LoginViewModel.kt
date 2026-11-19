package com.example.app_en_desarollo.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_en_desarollo.data.database.AppDatabase
import com.example.app_en_desarollo.model.LoginModel
import com.example.app_en_desarollo.model.TipoUsuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){

    var uiState by mutableStateOf(LoginModel())
        private set

    fun onTipoUsuarioChanged(tipo: TipoUsuario){
        uiState = uiState.copy(tipoUsuario = tipo,
            errorMessage = null)
    }

    fun onRutChanged(rut: String){
        uiState = uiState.copy(rut = rut,
            rutError = null,
            errorMessage = null)
    }

    fun onEmailChanged(email: String){
        uiState = uiState.copy(
            email = email,
            errorMessage = null,
            emailError = null)
    }

    fun onPasswordChanged(password: String){
        uiState = uiState.copy(
            password = password,
            errorMessage = null,
            passwordError = null)
    }

    private fun manejarLoginIncorrecto(){
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

    fun onLoginClick(
        context: Context,
        onSuccess: () -> Unit
    ){
        val state = uiState

        if(state.bloqueado){
            return
        }

        if(state.tipoUsuario == TipoUsuario.CLIENTE){
            if(state.email.isBlank()){
                uiState = uiState.copy(emailError = "Se debe INGRESAR el CORREO")
                return
            }
            //Validar correo (Contiene el @ y un .)
            if(!state.email.substringAfter("@").contains(".") || !state.email.contains("@")
                || state.email.endsWith(".") || state.email.endsWith("@")
                || state.email.startsWith(".") || state.email.startsWith("@")) {
                uiState = uiState.copy(emailError = "El correo debe ser valido")
                return
            }
        }
        else{
            if(state.rut.isBlank()){
                uiState = uiState.copy(rutError = "Se debe INGRESAR el RUT")
                return
            }
        }
        if(state.password.isBlank()){
            uiState = uiState.copy(passwordError = "Se debe INGRESAR la Contraseña")
            return
        }

        viewModelScope.launch {

            val db = AppDatabase.getDatabase(context)

            if(state.tipoUsuario == TipoUsuario.CLIENTE){
                val cliente = db.clienteDao().loginCliente(
                    state.email,
                    state.password
                )
                if (cliente != null) {

                    uiState = uiState.copy(
                        intentosFallidos = 0,
                        bloqueado = false,
                        emailError = null,
                        passwordError = null,
                        errorMessage = null
                    )

                    onSuccess()

                } else {

                    manejarLoginIncorrecto()
                }
            }
        else {

            val empresa = db.empresaDao().loginEmpresa(
                state.rut,
                state.password
            )
                if(empresa != null){
                    uiState = uiState.copy(
                        intentosFallidos = 0,
                        bloqueado = false,
                        rutError = null,
                        passwordError = null,
                        errorMessage = null
                    )
                    onSuccess()
                }
                else{

                    manejarLoginIncorrecto()
                }
            }
        }
    }
}