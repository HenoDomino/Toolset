package club.aborigen.general

import android.util.Log

private const val TAG = "ABO"

object Blog {

    fun d(str: String) {
        Log.d(TAG, str)
    }

    fun i(str: String) {
        Log.i(TAG, str)
    }

    fun w(str: String) {
        Log.w(TAG, str)
    }

    fun e(str: String) {
        Log.e(TAG, str)
    }
}
