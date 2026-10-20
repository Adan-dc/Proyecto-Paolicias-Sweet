package com.example.app_en_desarollo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,
    val empresa: String?,
    val telefono: String,
    val email: String,
    val password: String
)