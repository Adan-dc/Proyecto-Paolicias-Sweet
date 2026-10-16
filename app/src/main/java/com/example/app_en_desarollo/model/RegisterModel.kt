package com.example.app_en_desarollo.model

import android.os.Message

data class RegisterModel(
    val name: String = "",
    val empresa: String = "",
    val telefono: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String? = null
)