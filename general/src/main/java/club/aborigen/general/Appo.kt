package club.aborigen.general
import android.app.*
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.FirebaseApp

class Appo : Application() {

    val sharedPreferences: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(applicationContext) }
    val alarmPlayer: AlarmPlayer by lazy { AlarmPlayer(applicationContext) }
    var TAG = "ABL"

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        _instance = this
    }

    fun isAppVisible(): Boolean {
        return ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
    }

    companion object {
        private lateinit var _instance: Appo
            private set
        fun get(): Appo {
            return _instance
        }
    }

    init {
        _instance = this
    }
}
