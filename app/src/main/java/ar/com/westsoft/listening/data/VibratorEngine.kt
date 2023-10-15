package ar.com.westsoft.listening.screen.keyboard.ar.com.westsoft.listening.data

import android.os.VibrationEffect
import android.os.Vibrator
import javax.inject.Inject

class VibratorEngine @Inject constructor(
    private val vibrator: Vibrator
) {
    fun vibrareTick() {
        vibrator.vibrate(
            VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
        )

    }
}