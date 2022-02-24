package club.aborigen.general

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

abstract class FullActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, windowInsets ->
                if(isFullScreen()) {
                    window.insetsController?.hide(WindowInsets.Type.navigationBars())
                    window.insetsController?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                } else if(isZeroTop()) {
                    val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                    val lp = view.layoutParams as ViewGroup.MarginLayoutParams
                    lp.setMargins(0, 0, 0, insets.bottom)
                    view.layoutParams = lp
                }
                WindowInsetsCompat.CONSUMED
            }
        } else {
            if(isFullScreen()) {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
                window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            } else if(isZeroTop()) {
                window.decorView.systemUiVisibility = (SYSTEM_UI_FLAG_LAYOUT_STABLE or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        try {
            val newOverride = Configuration(newBase?.resources?.configuration)
            newOverride.fontScale = 1.0f
            applyOverrideConfiguration(newOverride)
        } catch (e: Exception) {
            Blog.e("Attach base context: $e")
        }
    }

    fun startWithFade(ii: Intent) {
        startActivity(ii)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    fun startWithFade(launcher: ActivityResultLauncher<Intent>, ii: Intent) {
        val anim = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out)
        launcher.launch(ii, anim)
    }

    fun startImmediately(ii: Intent) {
        startActivity(ii)
        overridePendingTransition(0, 0)
    }

    override fun onPause() {
        super.onPause()
        if(isFinishing) {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        } else {
            dismissDialogs(supportFragmentManager)
        }
    }

    private fun dismissDialogs(mgr: FragmentManager) {
        mgr.fragments.forEach {
            if(it is DialogFragment) {
                it.dismiss()
            } else {
                dismissDialogs(it.childFragmentManager)
            }
        }
    }

    open fun isZeroTop(): Boolean {
        return false
    }

    open fun isFullScreen(): Boolean {
        return false
    }
}
