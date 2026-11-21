package com.example.app_en_desarollo.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "clientes",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["idUsuario"],
            childColumns = ["idUsuario"],
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [
        Index(value = ["idUsuario"], unique = true),
        Index(value = ["email"], unique = true)
    ]
)
data class ClienteEntity(

    @PrimaryKey(autoGenerate = true)
    val idCliente: Int = 0,

    val idUsuario: Int,

    val nombre: String,

    val email: String

)
