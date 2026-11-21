package com.example.app_en_desarollo.model

import android.os.Message
enum class TipoUsuario{
    CLIENTE,
    EMPRESA,
    
    ADMIN
}
data class RegisterModel(
    val tipoUsuario: TipoUsuario = TipoUsuario.CLIENTE,

    // Cliente
    val name: String = "",
    val email: String = "",

    // Empresa
    val rut: String = "",
    val razonSocial: String = "",

    // Comunes
    val telefono: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val errorMessage: String? = null,

    val nameError: String? = null,
    val telefonoError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,

    val rutError: String? = null,
    val razonSocialError: String? = null
)