package com.example.app_en_desarollo.model

import android.os.Message

data class RegisterModel(
    val name: String = "",
    val empresa: String = "",
    val telefono: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String? = null,

    val nameError: String? = null,
    val telefonoError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
)