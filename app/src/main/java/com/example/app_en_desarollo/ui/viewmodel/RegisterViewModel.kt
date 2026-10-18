package com.example.app_en_desarollo.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_en_desarollo.data.database.AppDatabase
import com.example.app_en_desarollo.data.local.entity.UserEntity
import com.example.app_en_desarollo.model.RegisterModel
import kotlinx.coroutines.launch


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

    fun onRegisterClick(context: Context, onSuccess: () -> Unit){
        val state = uiState

        //Validar datos vacios
        if(state.name.isBlank()|| state.telefono.isBlank() || state.email.isBlank() || state.password.isBlank() || state.confirmPassword.isBlank()){
            uiState = uiState.copy(errorMessage = "Favor completar todos los campos.")
            return
        }

        //Validar telefono (Contiene el +56 9 y tiene 8 digitos)
        if(!state.telefono.startsWith("+56 9") || state.telefono.length != 14) {
            uiState = uiState.copy(
                errorMessage = "El numero debe tener formato +56 9 XXXXXXXX")
            return
        }

        //Validar correo (Contiene el @ y un .)
        if(!state.email.substringAfter("@").contains(".") || !state.email.contains("@")
            || state.email.endsWith(".") || state.email.endsWith("@")
            || state.email.startsWith(".") || state.email.startsWith("@")) {
            uiState = uiState.copy(errorMessage = "El correo debe ser valido")
            return
        }

        //validamos coincidencias en contraseñas
        if(state.password != state.confirmPassword){
            uiState = uiState.copy(errorMessage = "Las contraseñas deben se iguales.")
            return
        }

        //Si las validaciones son correctas
        viewModelScope.launch{
            val db = AppDatabase.getDatabase(context)
            val usuarioDao = db.usuarioDao()
            val usuarioExistente = usuarioDao.buscarPorEmail(state.email)
            if(usuarioExistente != null){
                uiState = uiState.copy(
                    errorMessage = "El correo ya esta registrado"
                )
            }
            else {
                val nuevoUsuario = UserEntity(
                    nombre = state.name,
                    empresa = state.empresa.ifBlank { null },
                    telefono = state.telefono,
                    email = state.email,
                    password = state.password
                )

                usuarioDao.insertarUsuario(nuevoUsuario)

                uiState = uiState.copy(
                    errorMessage = null
                )
                onSuccess()
            }
        }
    }
}