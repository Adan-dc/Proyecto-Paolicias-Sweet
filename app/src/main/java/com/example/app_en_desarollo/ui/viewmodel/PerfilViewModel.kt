package com.example.app_en_desarollo.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_en_desarollo.data.database.AppDatabase
import com.example.app_en_desarollo.data.local.entity.UserEntity
import com.example.app_en_desarollo.data.preferences.SesionBiometrica
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {

    var usuario by mutableStateOf<UserEntity?>(null)
        private set

    fun cargarUsuario(context: Context) {

        val email = SesionBiometrica.obtenerEmail(context)

        if (email != null) {
            viewModelScope.launch {
                val db = AppDatabase.getDatabase(context)
                usuario = db.usuarioDao().buscarPorEmail(email)
            }
        }
    }
}