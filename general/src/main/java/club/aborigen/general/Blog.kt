package club.aborigen.general

import android.util.Log

object Blog {

    fun d(str: String) {
        Log.d(Appo.get().TAG, str)
    }

    fun i(str: String) {
        Log.i(Appo.get().TAG, str)
    }

    fun w(str: String) {
        Log.w(Appo.get().TAG, str)
    }

    fun e(str: String) {
        Log.e(Appo.get().TAG, str)
    }
}
