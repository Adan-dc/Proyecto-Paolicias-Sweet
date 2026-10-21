package com.example.app_en_desarollo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservas")
data class ReservaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuarioEmail: String = "",   // correo del usuario dueño de la reserva
    val tipoEvento: String,
    val fecha: String,
    val hora: String,
    val asistentes: Int,
    val tieneObservaciones: Boolean,
    val observaciones: String
)
