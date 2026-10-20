package com.example.app_en_desarollo.ui.screen

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

fun inicarBiomeria(
    activity: FragmentActivity,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
){
    val executor = ContextCompat.getMainExecutor(activity)

    val biometricPrompt = BiometricPrompt(
        activity,
        executor,
        object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onError("Huella no reconocida")
            }

            override fun onAuthenticationError(
                errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onError(errString.toString())
            }
        }
    )
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Inicio de sesión biométrico")
        .setSubtitle("Usa tu huella para ingresar")
        .setNegativeButtonText("Cancelar")
        .build()

    biometricPrompt.authenticate(promptInfo)
}
fun biometriaDisponible(activity: FragmentActivity): Boolean {

    val biometricManager = BiometricManager.from(activity)

    val resultado = biometricManager.canAuthenticate(
        BiometricManager.Authenticators.BIOMETRIC_STRONG
    )

    return resultado == BiometricManager.BIOMETRIC_SUCCESS
}