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
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {

    var uiState by mutableStateOf(PerfilModel())
        private set

    fun cargarUsuario(context: Context) {

        val email = SesionBiometrica.obtenerEmail(context)

        if (email == null) {
            uiState = uiState.copy(
                cargando = false
            )
            return
        }

        viewModelScope.launch {

            val db = AppDatabase.getDatabase(context)

            val usuario = db.usuarioDao().buscarPorEmail(email)

            if (usuario != null) {

                uiState = PerfilModel(
                    nombre = usuario.nombre,
                    empresa = usuario.empresa,
                    telefono = usuario.telefono,
                    email = usuario.email,
                    cargando = false
                )

            } else {

                uiState = uiState.copy(
                    cargando = false
                )
            }
        }
        }
}