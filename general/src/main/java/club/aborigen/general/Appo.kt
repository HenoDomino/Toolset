package club.aborigen.general
import android.app.*
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner

class Appo : Application() {

    val sharedPreferences: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(applicationContext) }

    override fun onCreate() {
        super.onCreate()
        _instance = this

        //NotificationUtils.createChannel(getString(R.string.notification_channel_id), getString(R.string.notification_channel_name))
    }

    fun isAppVisible(): Boolean {
        return ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
    }

    companion object {
        lateinit var _instance: Appo
            private set
        fun get(): Appo {
            return _instance
        }
    }

    init {
        _instance = this
    }
}
