package com.example.app_en_desarollo.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_en_desarollo.data.database.AppDatabase
import com.example.app_en_desarollo.model.LoginModel
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){

    var uiState by mutableStateOf(LoginModel())
        private set

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

    fun onLoginClick(
        context: Context,
        onSuccess: () -> Unit
    ){
        val state = uiState

        if(state.email.isBlank()){
            uiState = uiState.copy(emailError = "Se debe INGRESAR el CORREO")
            return
        }

        if(state.password.isBlank()){
            uiState = uiState.copy(passwordError = "Se debe INGRESAR la Contraseña")
            return
        }

        //Validar correo (Contiene el @ y un .)
        if(!state.email.substringAfter("@").contains(".") || !state.email.contains("@")
            || state.email.endsWith(".") || state.email.endsWith("@")
            || state.email.startsWith(".") || state.email.startsWith("@")) {
            uiState = uiState.copy(emailError = "El correo debe ser valido")
            return
        }

        viewModelScope.launch {

            val db = AppDatabase.getDatabase(context)

            val usuarioDao = db.usuarioDao()

            val usuario = usuarioDao.login(
                state.email,
                state.password
            )

            if (usuario != null) {

                uiState = uiState.copy(
                    emailError = null,
                    passwordError = null,
                    errorMessage = null
                )

                onSuccess()

            } else {

                uiState = uiState.copy(
                    errorMessage = "Correo o contraseña incorrectos."
                )
            }
        }
    }
}