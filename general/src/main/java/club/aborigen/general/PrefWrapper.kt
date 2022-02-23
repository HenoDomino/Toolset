package club.aborigen.general

object PrefWrapper {

    fun contains(key: String): Boolean {
        return Appo.get().sharedPreferences.contains(key)
    }

    fun remove(key:String) {
        try {
            val editor = Appo.get().sharedPreferences.edit()
            editor?.remove(key)
            editor?.apply()
        } catch(e: Exception) {
            Blog.e("PrefWrapper remove: $e")
        }
    }

    fun set(key: String?, value: String?) {
        try {
            val editor = Appo.get().sharedPreferences.edit()
            editor?.putString(key, value)
            editor?.apply()
        } catch(e: Exception) {
            Blog.e("PrefWrapper set: $e")
        }
    }

    fun get(key: String, defValue: String): String {
        return try {
            return Appo.get().sharedPreferences.getString(key, defValue)?:defValue
        } catch (e: Exception) {
            defValue
        }
    }

    fun set(key: String?, value: Int) {
        try {
            val editor = Appo.get().sharedPreferences.edit()
            editor?.putInt(key, value)
            editor?.apply()
        } catch(e: Exception) {
            Blog.e("PrefWrapper set: $e")
        }
    }

    fun get(key: String?, defValue: Int): Int {
        return try {
            Appo.get().sharedPreferences.getInt(key, defValue)?:defValue
        } catch (e: Exception) {
            defValue
        }
    }

    fun set(key: String?, value: Long) {
        try {
            val editor = Appo.get().sharedPreferences.edit()
            editor?.putLong(key, value)
            editor?.apply()
        } catch(e: Exception) {
            Blog.e("PrefWrapper set: $e")
        }
    }

    fun get(key: String?, defValue: Long): Long {
        return try {
            Appo.get().sharedPreferences.getLong(key, defValue)?:defValue
        } catch (e: Exception) {
            defValue
        }
    }

    fun set(key: String?, value: Boolean) {
        try {
            val editor = Appo.get().sharedPreferences.edit()
            editor?.putBoolean(key, value)
            editor?.apply()
        } catch(e: Exception) {
            Blog.e("PrefWrapper set: $e")
        }
    }

    fun get(key: String?, defValue: Boolean): Boolean {
        return try {
            Appo.get().sharedPreferences.getBoolean(key, defValue)?:defValue
        } catch (e: Exception) {
            defValue
        }
    }

    fun set(key: String?, value: Float) {
        try {
            val editor = Appo.get().sharedPreferences.edit()
            editor?.putFloat(key, value)
            editor?.apply()
        } catch(e: Exception) {
            Blog.e("PrefWrapper set: $e")
        }
    }

    fun set(key: String?, value: Double) {
        try {
            val editor = Appo.get().sharedPreferences.edit()
            editor?.putString(key, value.toString())
            editor?.apply()
        } catch(e: Exception) {
            Blog.e("PrefWrapper set: $e")
        }
    }

    fun get(key: String?, defValue: Float): Float {
        return try {
            Appo.get().sharedPreferences.getFloat(key, defValue)?:defValue
        } catch (e: Exception) {
            defValue
        }
    }

    fun get(key: String?, defValue: Double): Double {
        return try {
            Appo.get().sharedPreferences.getString(key, defValue.toString())?.toDouble()?:defValue
        } catch (e: Exception) {
            defValue
        }
    }
}
