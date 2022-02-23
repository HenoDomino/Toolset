package club.aborigen.general

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.ContentViewCallback

class PopupView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs), ContentViewCallback {
    val msg: TextView

    init {
        View.inflate(context, R.layout.view_snack, this)
        msg = findViewById(R.id.snack_msg)
    }

    override fun animateContentIn(delay: Int, duration: Int) {}

    override fun animateContentOut(delay: Int, duration: Int) {}
}

class Popup(parent: ViewGroup, content: PopupView) :
    BaseTransientBottomBar<Popup>(parent, content, content) {

    init {
        view.setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.transparent))
        val pxs = Converter.dp2pixel(32)
        val pxb = Converter.dp2pixel(64)
        view.setPadding(pxs, 0, pxs, pxb)
    }

    companion object {

        fun show(activity: Activity, text: String, success: Boolean, length: Int = LENGTH_LONG) {
            val viewGroup = activity.window.decorView.rootView as ViewGroup
            val customView = PopupView(viewGroup.context, null)
            val bg = if(success) R.drawable.bg_ok_radius_8 else R.drawable.bg_error_radius_8
            customView.background = ContextCompat.getDrawable(viewGroup.context, bg)
            customView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            customView.msg.text = text

            val asb = Popup(viewGroup, customView)
            asb.duration = length
            asb.show()
        }
    }
}
