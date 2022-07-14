package com.example.dubaipractical.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class GlobalMethods @Inject constructor() {

    val dbDateFormate =  SimpleDateFormat("yyyy-MM-dd")
    val dateFormatToDisplay = SimpleDateFormat("dd-MMM-yyyy")

    fun getCurrentDateAndTime(): String {
        return SimpleDateFormat("dd-MMM-yyyy HH:mm a", Locale.getDefault()).format(Date())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isInternetAvailable(context: Context): Boolean {
        var isOnline = false
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        try {
            val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
            isOnline =  capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return isOnline
    }

    /**
     * We hvae converted yyyy-mm-dd to dd-mm-yy date format to show in UI part.
     */
    fun getDateformateToDisplay(oldFormateDate: String) : String {
        val date:Date = dbDateFormate.parse(oldFormateDate)
        return dateFormatToDisplay.format(date)
    }
}