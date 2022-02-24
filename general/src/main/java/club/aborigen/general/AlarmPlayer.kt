package club.aborigen.general

import android.app.Application
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlin.math.ln

class AlarmPlayer(val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun play(music: Int, volume: Int) {
        stop()
        val amg = context.getSystemService(Application.AUDIO_SERVICE) as AudioManager
        if(!(amg.mode==AudioManager.MODE_IN_CALL || amg.mode==AudioManager.MODE_RINGTONE)) {
            val attrs = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()

            val audioSessionId = amg.generateAudioSessionId()
            mediaPlayer = MediaPlayer.create(context, music, attrs, audioSessionId)
            val maxVolume = 100.0
            val vl = (1.0 - ln(maxVolume - volume) / ln(maxVolume)).toFloat()
            mediaPlayer?.setVolume(vl, vl)
            mediaPlayer?.isLooping = false
            mediaPlayer?.start()
        } else {
            Blog.w("Skipping audio playback, phone is in call")
        }
    }

    fun stop() {
        if(mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
        }
        mediaPlayer?.reset()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun vibrate(pattern: LongArray) { //longArrayOf(500, 500, 200, 500)
        val context = Appo.get().applicationContext
        val vibrator = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibe = VibrationEffect.createWaveform(pattern, -1)
            vibrator.vibrate(vibe, AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build())
        } else {
            vibrator.vibrate(pattern, -1)
        }
    }

    fun stopVibrate() {
        val vibrator = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        vibrator.cancel()
    }
}
