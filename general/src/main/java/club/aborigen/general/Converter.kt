package club.aborigen.general

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object Converter {

    fun dp2pixel(dp: Int): Int {
        return dp * Appo.get().resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
    }

    fun pixel2dp(px: Int): Int {
        return px * DisplayMetrics.DENSITY_DEFAULT / Appo.get().resources.displayMetrics.densityDpi
    }

    fun drawable2Bitmap(did: Int, maxSize: Int): Bitmap {
        val drawable = ContextCompat.getDrawable(Appo.get(), did)!!
        val wrappedDrawable = DrawableCompat.wrap(drawable);
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val width = maxSize*Appo.get().resources.displayMetrics.density.toInt()
        val height = maxSize*Appo.get().resources.displayMetrics.density.toInt()
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        wrappedDrawable.setBounds(0, 0, canvas.width, canvas.height)
        wrappedDrawable.draw(canvas)
        return bitmap
    }

    fun timeToString(tm: Date): String {
        val timeFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())
        return timeFormat.format(tm)
    }

    fun parseTime24(s: String): Calendar {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val cld = Calendar.getInstance()
        try {
            val cldTime = Calendar.getInstance()
            cldTime.time = timeFormat.parse(s)
            cld.set(Calendar.HOUR_OF_DAY, cldTime.get(Calendar.HOUR_OF_DAY))
            cld.set(Calendar.MINUTE, cldTime.get(Calendar.MINUTE))
            cld.set(Calendar.SECOND, 0)
            cld.set(Calendar.MILLISECOND, 0)
        } catch (e: Exception) {
            Blog.e("Time parse 24 error: $e")
        }
        return cld
    }
}
