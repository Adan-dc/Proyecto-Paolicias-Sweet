package com.example.app_en_desarollo.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import com.example.app_en_desarollo.model.RegisterModel


class RegisterViewModel : ViewModel(){
    var uiState by mutableStateOf(RegisterModel())
        private set

    fun onNameChanged(name: String){
        uiState = uiState.copy(name = name, errorMessage = null)
    }

    fun onEmpresaChanged(empresa: String){
        uiState = uiState.copy(empresa = empresa, errorMessage = null)
    }

    fun onTelefonoChanged(telefono: String){
        uiState = uiState.copy(telefono = telefono, errorMessage = null)
    }

    fun onEmailChanged(email: String){
        uiState = uiState.copy(email = email, errorMessage = null)
    }

    fun onPasswordChanged(password: String){
        uiState = uiState.copy(password = password, errorMessage = null)
    }

    fun onConfirmPasswordChanged(confirmPassword: String){
        uiState = uiState.copy(confirmPassword = confirmPassword, errorMessage = null)
    }

    fun onRegisterClick(onSuccess: () -> Unit){
        val state = uiState

        //Validar datos vacios
        if(state.name.isBlank()|| state.telefono.isBlank() || state.email.isBlank() || state.password.isBlank()){
            uiState = uiState.copy(errorMessage = "Favor completar todos los campos.")
            return
        }

        //validamos coincidencias en contraseñas
        if(state.password != state.confirmPassword){
            uiState = uiState.copy(errorMessage = "Las contraseñas deben se iguales.")
            return
        }

        //Si las validaciones son correctas, enviamos hacia consola Log
        Log.d("RegisterVM", "Usuario Registrado: ${state.name} (${state.email})")
        uiState = uiState.copy(errorMessage = null)
        onSuccess()
    }
}