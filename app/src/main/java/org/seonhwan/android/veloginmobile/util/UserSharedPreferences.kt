package org.seonhwan.android.veloginmobile.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserSharedPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("", 0)

    fun getString(key: String, defValue: String?): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, value: String?) {
        prefs.edit { putString(key, value) }
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }

    fun setBoolean(key: String, value: Boolean) {
        prefs.edit { putBoolean(key, value) }
    }
}