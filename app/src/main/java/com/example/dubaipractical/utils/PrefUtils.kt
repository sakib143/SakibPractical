package com.example.dubaipractical.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Handles Shared Preferences through out the App
 */
@Suppress("unused")
class PrefUtils @Inject constructor(context: Context) {

    /**
     * Object of [android.content.SharedPreferences]
     * */

    private val appPreference = context.getSharedPreferences(Constant.APP_PREFERENCES, Context.MODE_PRIVATE)

    private val LAST_SYNC_TIME = "last_sync_time"

    fun getLastSyncTime() : String? {
        val data = appPreference.getString(LAST_SYNC_TIME,null)
        return data
    }

    fun saveLastSyncTime(dateTimne: String) {
        val editor: SharedPreferences.Editor = appPreference.edit()
        editor.putString(LAST_SYNC_TIME, dateTimne)
        editor.apply()
    }
}