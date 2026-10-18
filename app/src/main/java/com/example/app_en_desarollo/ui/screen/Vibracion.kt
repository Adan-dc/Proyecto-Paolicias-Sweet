package com.example.app_en_desarollo.ui.screen

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager


fun vibrarTelefono(context: Context){

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE)
        as VibratorManager

        val vibrator = vibratorManager.defaultVibrator

        vibrator.vibrate(
            VibrationEffect.createOneShot(
                300,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }
    else{
        val vibrator =
            context.getSystemService(Context.VIBRATOR_SERVICE)
        as Vibrator

        vibrator.vibrate(
            VibrationEffect.createOneShot(
                300,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }
}