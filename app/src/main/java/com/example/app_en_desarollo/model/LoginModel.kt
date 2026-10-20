package com.example.app_en_desarollo.model

import android.provider.ContactsContract

data class LoginModel(
    val email: String = "",
    val password: String = "",
    val errorMessage: String? = null,

    val emailError : String? = null,
    val passwordError: String? = null,

    val intentosFallidos : Int = 0,
    val bloqueado: Boolean = false

)