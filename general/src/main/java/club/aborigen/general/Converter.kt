package club.aborigen.general

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

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
}
