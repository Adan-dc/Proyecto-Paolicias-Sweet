package com.example.app_en_desarollo.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_en_desarollo.data.database.AppDatabase
import com.example.app_en_desarollo.data.preferences.SesionBiometrica
import com.example.app_en_desarollo.model.PerfilModel
import com.example.app_en_desarollo.model.TipoUsuario
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {

    var uiState by mutableStateOf(PerfilModel())
        private set

    fun cargarPerfil(context: Context) {

        viewModelScope.launch {

            val db = AppDatabase.getDatabase(context)

            val tipoUsuario = SesionBiometrica.obtenerTipoUsuario(context)

            val identificador = SesionBiometrica.obtenerIdentificador(context)

            if(tipoUsuario == null || identificador == null) {

                uiState = uiState.copy(
                    cargando = false
                )
                return@launch

            }
            if (tipoUsuario == TipoUsuario.CLIENTE) {

                val cliente =
                    db.clienteDao().buscarPorEmail(identificador)

                if (cliente != null) {

                    val usuario =
                        db.usuarioDao().buscarPorId(cliente.idUsuario)

                    uiState = uiState.copy(
                        tipoUsuario = TipoUsuario.CLIENTE,
                        nombre = cliente.nombre,
                        email = cliente.email,
                        telefono = usuario?.telefono ?: "",
                        cargando = false
                    )
                }

            } else {

                val empresa =
                    db.empresaDao().buscarPorRut(identificador)

                if (empresa != null) {

                    val usuario =
                        db.usuarioDao().buscarPorId(empresa.idUsuario)

                    uiState = uiState.copy(
                        tipoUsuario = TipoUsuario.EMPRESA,
                        razonSocial = empresa.razonSocial,
                        rut = empresa.rut,
                        telefono = usuario?.telefono ?: "",
                        cargando = false
                    )
                }
            }
        }
    }
}