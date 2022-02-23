package club.aborigen.general

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtils {

    fun hasConnections(): Pair<Boolean, Boolean> {
        val connectivityManager = Appo.get().getSystemService(ConnectivityManager::class.java)
        val currentNetwork = connectivityManager.activeNetwork
        val caps = connectivityManager.getNetworkCapabilities(currentNetwork)
        val hasWifi = caps?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)==true
        val hasCellular = caps?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)==true
        return Pair(hasWifi, hasCellular)
    }
}

