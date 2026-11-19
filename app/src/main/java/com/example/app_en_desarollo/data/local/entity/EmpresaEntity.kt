package com.example.app_en_desarollo.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "empresa",
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
        Index(value = ["rut"], unique = true)
    ]
)
data class EmpresaEntity(

    @PrimaryKey(autoGenerate = true)
    val idEmpresa: Int = 0,

    val idUsuario: Int,

    val rut: String,

    val razonSocial: String

)
