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
import com.example.app_en_desarollo.data.local.entity.ClienteEntity
import com.example.app_en_desarollo.data.local.entity.EmpresaEntity
import com.example.app_en_desarollo.data.local.entity.UserEntity
import com.example.app_en_desarollo.model.RegisterModel
import com.example.app_en_desarollo.model.TipoUsuario
import kotlinx.coroutines.launch


class RegisterViewModel : ViewModel(){
    var uiState by mutableStateOf(RegisterModel())
        private set

    fun onTipoUsuarioChanged(tipo: TipoUsuario){
        uiState = uiState.copy(
            tipoUsuario = tipo,
            errorMessage = null
        )
    }

    fun onRutChanged(rut: String){
        uiState = uiState.copy(rut = rut,
            rutError = null)
    }

    fun onRazonSocialChanged(razonSocial: String){
        uiState = uiState.copy(razonSocial = razonSocial,
            razonSocialError = null)
    }

    fun onNameChanged(name: String){
        uiState = uiState.copy(name = name,
            errorMessage = null,
            nameError = null)
    }

    fun onTelefonoChanged(telefono: String){
        uiState = uiState.copy(telefono = telefono,
            errorMessage = null,
            telefonoError = null)
    }

    fun onEmailChanged(email: String){
        uiState = uiState.copy(email = email,
            errorMessage = null,
            emailError = null)
    }

    fun onPasswordChanged(password: String){
        uiState = uiState.copy(password = password,
            errorMessage = null,
            passwordError = null)
    }

    fun onConfirmPasswordChanged(confirmPassword: String){
        uiState = uiState.copy(confirmPassword = confirmPassword,
            errorMessage = null,
            confirmPasswordError = null)
    }

    fun onRegisterClick(context: Context, onSuccess: () -> Unit){
        val state = uiState

        //validaciones comunes
        if(state.telefono.isBlank()) {
            uiState = uiState.copy(telefonoError = "Debe COLOCAR un TELEFONO")
            return
        }

        //Validar telefono (Contiene el +56 9 y tiene 8 digitos)
        if(!state.telefono.startsWith("+56 9") || state.telefono.length != 14) {
            uiState = uiState.copy(
                telefonoError = "El numero debe tener formato +56 9 XXXXXXXX")
            return
        }

        if(state.password.isBlank()) {
            uiState = uiState.copy(passwordError = "Debe COLOCAR una CONTRASEÑA")
            return
        }

        if(state.confirmPassword.isBlank()) {
            uiState = uiState.copy(confirmPasswordError = "se debe CONFIRMAR la CONTRASEÑA")
            return
        }

        //validamos coincidencias en contraseñas
        if(state.password != state.confirmPassword){
            uiState = uiState.copy(errorMessage = "Las contraseñas deben se iguales.")
            return
        }

        //validacion tipo cliente
        if(state.tipoUsuario == TipoUsuario.CLIENTE){

            if(state.name.isBlank()) {
                uiState = uiState.copy(nameError = "Debe COLOCAR un NOMBRE")
                return
            }

            if(state.email.isBlank()) {
                uiState = uiState.copy(emailError = "Debe COLOCAR un CORREO")
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
            if(state.rut.isBlank()) {
                uiState = uiState.copy(rutError = "Debe COLOCAR un RUT")
                return
            }

            if(state.razonSocial.isBlank()) {
                uiState = uiState.copy(razonSocialError = "Debe COLOCAR el RUBRO DE LA EMPRESA")
                return
            }

        }

        //Si las validaciones son correctas
        viewModelScope.launch{
            val db = AppDatabase.getDatabase(context)

            val usuarioDao = db.usuarioDao()
            val clienteDao = db.clienteDao()
            val empresaDao = db.empresaDao()

            if(state.tipoUsuario == TipoUsuario.CLIENTE){
                val clienteExistente = clienteDao.buscarPorEmail(state.email)
                if(clienteExistente != null) {
                    uiState = uiState.copy(
                        errorMessage = "El correo ya esta registrado"
                    )
                    return@launch
                }
            }
            else {
                val empresaExistente = empresaDao.buscarPorRut(state.rut)

                if(empresaExistente != null){
                    uiState = uiState.copy(
                        errorMessage = "El rut ya esta registrado"
                    )
                    return@launch
                }

            }
            val nuevoUsuario = UserEntity(
                password = state.password,
                telefono = state.telefono
            )

            val idUsuario = usuarioDao.insertarUsuario(nuevoUsuario).toInt()

            if(state.tipoUsuario == TipoUsuario.CLIENTE){
                val nuevoCliente = ClienteEntity(
                    idUsuario = idUsuario,
                    nombre = state.name,
                    email = state.email
                )
                clienteDao.insertarCliente(nuevoCliente)
            }
            else{
                val nuevaEmpresa = EmpresaEntity(
                    idUsuario = idUsuario,
                    rut = state.rut,
                    razonSocial = state.razonSocial
                )
                empresaDao.insertarEmpresa(nuevaEmpresa)
            }
            onSuccess()
        }
    }
}